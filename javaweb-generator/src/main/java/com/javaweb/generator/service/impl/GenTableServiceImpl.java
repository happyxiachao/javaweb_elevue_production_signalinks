// +----------------------------------------------------------------------
// | JavaWeb_EleVue_Pro前后端分离旗舰版框架 [ JavaWeb ]
// +----------------------------------------------------------------------
// | 版权所有 2021 上海JavaWeb研发中心
// +----------------------------------------------------------------------
// | 官方网站: http://www.javaweb.vip/
// +----------------------------------------------------------------------
// | 作者: 鲲鹏 <javaweb520@gmail.com>
// +----------------------------------------------------------------------
// | 免责声明:
// | 本软件框架禁止任何单位和个人用于任何违法、侵害他人合法利益等恶意的行为，禁止用于任何违
// | 反我国法律法规的一切平台研发，任何单位和个人使用本软件框架用于产品研发而产生的任何意外
// | 、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、附带
// | 或衍生的损失等)，本团队不承担任何法律责任。本软件框架只能用于公司和个人内部的法律所允
// | 许的合法合规的软件产品研发，详细声明内容请阅读《框架免责声明》附件；
// +----------------------------------------------------------------------

package com.javaweb.generator.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.exception.CustomException;
import com.javaweb.common.utils.DateUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.generator.constant.GenConstants;
import com.javaweb.generator.entity.GenTable;
import com.javaweb.generator.entity.GenTableColumn;
import com.javaweb.generator.mapper.GenTableColumnMapper;
import com.javaweb.generator.mapper.GenTableMapper;
import com.javaweb.generator.query.GenTableQuery;
import com.javaweb.generator.service.IGenTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaweb.generator.utils.CodeGenerateUtils;
import com.javaweb.generator.utils.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 代码生成业务表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements IGenTableService {

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private CodeGenerateUtils codeGenerateUtils;

    /**
     * 获取业务表列表
     *
     * @param genTableQuery 查询条件
     * @return
     */
    @Override
    public JsonResult getList(GenTableQuery genTableQuery) {
        IPage<GenTable> page = new Page<>(genTableQuery.getPage(), genTableQuery.getLimit());
        IPage<GenTable> pageData = genTableMapper.selectGenTableList(page, genTableQuery);
        return JsonResult.success(pageData, "操作成功");
    }

    /**
     * 获取数据库表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public IPage<GenTable> genDbTableList(GenTableQuery query) {
        IPage<GenTable> page = new Page<>(query.getPage(), query.getLimit());
        return genTableMapper.selectDbTableList(page, query);
    }

    /**
     * 查询据库列表
     *
     * @param tableNames 表数组
     * @return
     */
    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames) {
        return genTableMapper.selectDbTableListByNames(tableNames);
    }

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    @Transactional
    @Override
    public void importGenTable(List<GenTable> tableList) {
        String operName = "内部人员";//SecurityUtils.getUsername();
        for (GenTable table : tableList) {
            try {
                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                int row = genTableMapper.insertGenTable(table);
                if (row > 0) {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumn column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insertGenTableColumn(column);
                    }
                }
            } catch (Exception e) {
                log.error("表名 " + table.getTableName() + " 导入失败：", e);
            }
        }
    }

    /**
     * 根据表ID获取表信息
     *
     * @param tableId 表ID
     * @return
     */
    @Override
    public GenTable selectGenTableById(Integer tableId) {
        GenTable genTable = genTableMapper.selectGenTableById(tableId);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 设置代码生成其他选项
     *
     * @param genTable 待生成表
     */
    public void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
        if (StringUtils.isNotNull(paramsObj)) {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
        }
    }

    /**
     * 验证编辑信息
     *
     * @param genTable 生成表
     */
    @Override
    public void validateEdit(GenTable genTable) {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
            String options = "{}";//JSON.toJSONString(genTable.getParams());
            JSONObject paramsObj = JSONObject.parseObject(options);
            if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
                throw new CustomException("树编码字段不能为空");
            } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
                throw new CustomException("树父编码字段不能为空");
            } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
                throw new CustomException("树名称字段不能为空");
            }
        }
    }

    /**
     * 编辑业务表生成信息
     *
     * @param genTable 业务表
     */
    @Override
    public void updateGenTable(GenTable genTable) {
        String options = "{}";//JSON.toJSONString(genTable.getParams());
        genTable.setOptions(options);
        int row = genTableMapper.updateGenTable(genTable);
        if (row > 0) {
            for (GenTableColumn cenTableColumn : genTable.getColumns()) {
                genTableColumnMapper.updateGenTableColumn(cenTableColumn);
            }
        }
    }

    /**
     * 生成数据表
     *
     * @param tableNames 数据表
     * @return
     */
    @Override
    public JsonResult generatorCode(String[] tableNames) {
        Integer totalNum = 0;
        for (String tableName : tableNames) {
            // 查询表信息
            GenTable tableInfo = genTableMapper.selectGenTableByName(tableName);
            try {
                // 生成文件
                codeGenerateUtils.generateFile(tableInfo.getTableName(), tableInfo.getTableComment());
                totalNum++;
            } catch (Exception e) {

            }
        }
        return JsonResult.success(String.format("本地共生成【%s】个模块", totalNum));
    }

    /**
     * 删除业务表
     *
     * @param ids 业务表ID
     * @return
     */
    @Override
    public JsonResult delete(Integer[] ids) {
        Integer totalNum = 0;
        for (Integer id : ids) {
            GenTable entity = genTableMapper.selectById(id);
            if (entity == null) {
                return JsonResult.error("业务表不存在");
            }
            entity.setUpdateUser(0);
            entity.setUpdateTime(DateUtils.now());
            entity.setMark(0);
            boolean result = updateById(entity);
            if (result) {
                totalNum++;
            }
        }
        if (totalNum != ids.length) {
            return JsonResult.error("删除失败");
        }
        return JsonResult.success("删除成功");
    }
}
