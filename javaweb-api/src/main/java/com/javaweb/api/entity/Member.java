package com.javaweb.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaweb.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-12
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
     * 性别（1男 2女 3未知）
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
     * 推送的别名
     */
    private String deviceCode;

    /**
     * 用户状态(1登录 2登出)
     */
    private Integer loginStatus;

    /**
     * 推送的别名
     */
    private String pushAlias;

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
     * 最近登录IP
     */
    private String loginIp;

    /**
     * 登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /**
     * 上次登录地点
     */
    private String loginRegion;

    /**
     * 登录总次数
     */
    private Integer loginCount;


}
