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

package com.javaweb.system.vo.city;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 高德城市表列表Vo
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-01-07
 */
@Data
public class CityListVo {

    /**
     * 高德城市表ID
     */
    private Integer id;

    /**
     * ID
     */
    private Integer cityId;

    /**
     * 父级ID
     */
    private Integer pid;

    /**
     * 层级：1省 2市 3区
     */
    private Integer level;

    /**
     * 省市区名称
     */
    private String name;

    /**
     * 简称
     */
    private String simple;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 简拼
     */
    private String code;

    /**
     * 首字母
     */
    private String firstChar;

    /**
     * 原城市ID
     */
    private Integer oldCityId;

    /**
     * 有无子级：1有 2无
     */
    private Integer hasChild;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 1-有效 2-无效
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 是否删除 1未删除 0已删除
     */
    private Integer mark;

}