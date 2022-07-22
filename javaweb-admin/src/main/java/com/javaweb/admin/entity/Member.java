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

package com.javaweb.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaweb.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_member")
public class Member extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String openid;

    /**
     * 会员账号
     */
    private String username;

    /**
     * 会员密码
     */
    private String password;

    /**
     * 会员等级
     */
    private Integer memberLevel;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别：1男 2女 3保密
     */
    private Integer gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    /**
     * 户籍省份编号
     */
    private String provinceCode;

    /**
     * 户籍城市编号
     */
    private String cityCode;

    /**
     * 户籍区/县编号
     */
    private String districtCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 城市集合
     */
    @TableField(exist = false)
    private String[] city;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 设备类型：1苹果 2安卓 3WAP站 4PC站 5后台添加
     */
    private Integer device;

    /**
     * 设备编号
     */
    private String deviceCode;

    /**
     * 推送的别名
     */
    private String pushAlias;

    /**
     * 用户状态(1登录 2登出)
     */
    private Integer loginStatus;

    /**
     * 来源：1、APP注册；2、后台添加；
     */
    private Integer source;

    /**
     * 是否启用 1、启用  2、停用
     */
    private Integer status;

    /**
     * 客户端版本号
     */
    private String appVersion;

    /**
     * 我的推广码
     */
    private String code;

    /**
     * 上次登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /**
     * 最近登录IP
     */
    private String loginIp;

    /**
     * 上次登录地点
     */
    private String loginRegion;

    /**
     * 登录总次数
     */
    private Integer loginCount;

}
