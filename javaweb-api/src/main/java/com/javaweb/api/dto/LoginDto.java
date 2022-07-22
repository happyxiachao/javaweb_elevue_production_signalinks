package com.javaweb.api.dto;

import lombok.Data;

/**
 * 会员登录Dto
 */
@Data
public class LoginDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
