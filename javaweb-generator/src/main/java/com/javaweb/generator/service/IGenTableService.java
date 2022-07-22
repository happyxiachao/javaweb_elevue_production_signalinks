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

package com.javaweb.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.generator.entity.GenTable;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaweb.generator.query.GenTableQuery;

import java.util.List;

/**
 * <p>
 * 代码生成业务表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
public interface IGenTableService extends IService<GenTable> {

    /**
     * 根据查询条件获取数据列表
     *
     * @param genTableQuery 查询条件
     * @return
     */
    JsonResult getList(GenTableQuery genTableQuery);

    /**
     * 获取数据库表
     *
     * @param query 查询条件
     * @return
     */
    IPage<GenTable> genDbTableList(GenTableQuery query);

    /**
     * 查询据库列表
     *
     * @param tableNames 表数组
     * @return
     */
    List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    void importGenTable(List<GenTable> tableList);

    /**
     * 根据表ID获取表信息
     *
     * @param tableId 表ID
     * @return
     */
    GenTable selectGenTableById(Integer tableId);

    /**
     * 业务表保存参数校验
     *
     * @param Table 生成表
     */
    void validateEdit(GenTable Table);

    /**
     * 更新业务表信息
     *
     * @param Table 业务表
     */
    void updateGenTable(GenTable Table);

    /**
     * 生成代码
     *
     * @param tableNames 数据表
     * @return
     */
    JsonResult generatorCode(String[] tableNames);

    /**
     * 删除记录
     *
     * @param ids 业务表ID
     * @return
     */
    JsonResult delete(Integer[] ids);

}
