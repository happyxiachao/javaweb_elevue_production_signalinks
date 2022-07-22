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

package com.javaweb.generator.mapper;

import com.javaweb.generator.entity.GenTableColumn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 代码生成业务表字段 Mapper 接口
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
public interface GenTableColumnMapper extends BaseMapper<GenTableColumn> {

    /**
     * 根据表名查询列信息
     *
     * @param tableName 数据表名
     * @return
     */
    List<GenTableColumn> selectDbTableColumnsByName(String tableName);

    /**
     * 插入数据表列
     *
     * @param TableColumn 数据表列
     * @return
     */
    int insertGenTableColumn(GenTableColumn TableColumn);

    /**
     * 获取表字段列表
     *
     * @param tableId 表ID
     * @return
     */
    List<GenTableColumn> selectGenTableColumnListByTableId(Integer tableId);

    /**
     * 修改业务表字段
     *
     * @param TableColumn 表字段
     * @return
     */
    int updateGenTableColumn(GenTableColumn TableColumn);

}
