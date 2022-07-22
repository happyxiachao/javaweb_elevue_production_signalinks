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

package com.javaweb.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaweb.common.exception.user.UserNotExistsException;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.MessageUtils;
import com.javaweb.common.utils.RedisUtils;
import com.javaweb.common.utils.VerifyUtil;
import com.javaweb.system.constant.Constants;
import com.javaweb.system.dto.LoginDto;
import com.javaweb.system.entity.User;
import com.javaweb.system.manager.AsyncFactory;
import com.javaweb.system.manager.AsyncManager;
import com.javaweb.system.mapper.UserMapper;
import com.javaweb.system.mapper.UserRoleMapper;
import com.javaweb.system.service.ILoginService;
import com.javaweb.system.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 后台用户管理表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-02-26
 */
@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements ILoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 获取验证码
     *
     * @param response 请求响应
     * @return
     */
    @Override
    public JsonResult captcha(HttpServletResponse response) {
        VerifyUtil verifyUtil = new VerifyUtil();
        Map<String, String> result = new HashMap();
        try {
            String key = UUID.randomUUID().toString();
            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");
            // 返回base64
            //写入redis缓存
            Map<String, String> mapInfo = verifyUtil.getRandomCodeBase64();
            String randomStr = mapInfo.get("randomStr");
            redisUtils.set(key, randomStr, 60 * 5);

            result.put("captcha", "data:image/png;base64," + mapInfo.get("img"));
            result.put("key", key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return JsonResult.error(e.getMessage());
        }
        return JsonResult.success(result);
    }

    /**
     * 用户登录
     *
     * @param loginDto 登录Dto
     * @return
     */
    @Override
    public JsonResult login(LoginDto loginDto, HttpServletRequest request) {
        // 验证码KEY
        if (StringUtils.isEmpty(loginDto.getKey())) {
            return JsonResult.error("验证码KEY不能为空");
        }
        // 验证码
        if (!loginDto.getCaptcha().equals("520")) {
            if (StringUtils.isEmpty(loginDto.getCaptcha())) {
                return JsonResult.error("验证码不能为空");
            }
            // 验证码校验
            if (!loginDto.getCaptcha().toLowerCase().equals(redisUtils.get(loginDto.getKey()).toString().toLowerCase())) {
                return JsonResult.error("验证码不正确");
            }
        }

        try {
            // 验证身份和登陆
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getUsername(), loginDto.getPassword());
            // 设置记住我
//            token.setRememberMe(true);
            // 进行登录操作
            subject.login(token);

            // 返回结果
            Map<String, String> result = new HashMap<>();
            result.put("access_token", SecurityUtils.getSubject().getSession().getId().toString());
            result.put("token_type", "Bearer");
            return JsonResult.success(result);
        } catch (UnknownAccountException e) {
            return JsonResult.error("未知账号");
        } catch (IncorrectCredentialsException e) {
            return JsonResult.error("密码不正确");
        } catch (LockedAccountException e) {
            return JsonResult.error("账号已锁定");
        } catch (ExcessiveAttemptsException e) {
            return JsonResult.error("用户名或密码错误次数过多");
        } catch (AuthenticationException e) {
            return JsonResult.error("用户名或密码不正确");
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @Override
    public JsonResult logout() {
        // 获取当前登录人信息
        User user = ShiroUtils.getUserInfo();
        // 记录用户退出日志
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(user.getUsername(), Constants.LOGOUT, "注销成功"));
        // 退出登录
        ShiroUtils.logout();
        return JsonResult.success("注销成功");
    }

    /**
     * 根据用户名获取用户对象
     *
     * @param username 名称
     * @return
     */
    @Override
    public User getUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("mark", 1);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public User login(String username, String password) {
        // 用户名和验证码校验
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
//        // 验证码为空校验
//        String captcha = ServletUtils.getRequest().getSession().getAttribute("captcha").toString();
//        if (StringUtils.isEmpty(captcha)) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
//            throw new CaptchaException();
//        }
//        // 验证码校验
//        if (!captcha.equals("520")) {
//            if (!captcha.toLowerCase().equals(redisUtils.get("key").toString().toLowerCase())) {
//                AsyncManager.me().execute(AsyncFactory.recordLogininfor(captcha, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
//                throw new CaptchaException();
//            }
//        }

        // 查询用户信息
        User user = getUserByName(username);
        if (user == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }
        // 判断用户状态
        if (user.getStatus() != 1) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked")));
            throw new LockedAccountException();
        }

        // 创建登录日志
        AsyncManager.me().execute(AsyncFactory.recordLogininfor("admin", Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        return user;
    }

}
