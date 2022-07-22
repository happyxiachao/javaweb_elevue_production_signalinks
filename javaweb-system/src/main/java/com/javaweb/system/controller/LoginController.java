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

package com.javaweb.system.controller;

import com.javaweb.common.common.BaseController;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.system.dto.LoginDto;
import com.javaweb.system.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 系统登录 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private ILoginService loginService;

    /**
     * 获取验证码
     *
     * @param response 网络请求
     * @return
     */
    @GetMapping("/captcha")
    public JsonResult captcha(HttpServletResponse response) {
        return loginService.captcha(response);
    }

    /**
     * 系统登录
     *
     * @param loginDto 参数
     * @param request  网络请求
     * @return
     */
    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        return loginService.login(loginDto, request);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/logout")
    public JsonResult logout() {
        return loginService.logout();
    }

    /**
     * 用户未登录
     *
     * @return
     */
    @GetMapping("/un_auth")
    public JsonResult unAuth() {
        return JsonResult.error(HttpStatus.UNAUTHORIZED, "");
    }

    /**
     * 用户无权限
     *
     * @return
     */
    @GetMapping("/unauthorized")
    public JsonResult unauthorized() {
        return JsonResult.error(HttpStatus.FORBIDDEN, "用户无权限");
    }

}
