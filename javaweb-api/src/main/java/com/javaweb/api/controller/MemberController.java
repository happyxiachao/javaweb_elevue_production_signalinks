package com.javaweb.api.controller;


import com.javaweb.api.dto.LoginDto;
import com.javaweb.api.service.IMemberService;
import com.javaweb.common.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.javaweb.common.common.BaseController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-12
 */
@RestController
@RequestMapping("/member")
public class MemberController extends BaseController {

    @Autowired
    private IMemberService memberService;

    /**
     * 会员登录
     *
     * @param loginDto 参数
     * @return
     */
    @PostMapping("/login")
    private JsonResult login(@RequestBody LoginDto loginDto) {
        return memberService.login(loginDto);
    }

}
