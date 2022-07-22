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

package com.javaweb.system.vo.article;

import lombok.Data;

/**
 * <p>
 * 文章管理表表单Vo
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-11
 */
@Data
public class ArticleInfoVo {

    /**
     * 文章管理表ID
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 首张图片编号
     */
    private String cover;

    /**
     * 站点ID
     */
    private Integer itemId;

    /**
     * 站点名称
     */
    private String itemName;

    /**
     * 栏目ID
     */
    private Integer cateId;

    /**
     * 栏目列表
     */
    private String cateName;

    /**
     * 文章导读
     */
    private String intro;

    /**
     * 图集
     */
    private String imgs;

    /**
     * 图片列表
     */
    private String[] imgsList;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

    /**
     * 浏览量
     */
    private Integer browse;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 添加时间
     */
    private Integer createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    private Integer updateTime;

    /**
     * 有效标记
     */
    private Integer mark;

}