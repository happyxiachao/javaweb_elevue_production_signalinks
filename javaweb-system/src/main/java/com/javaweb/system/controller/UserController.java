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


import com.javaweb.common.annotation.Log;
import com.javaweb.common.common.BaseController;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.enums.LogType;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.system.dto.ResetPwdDto;
import com.javaweb.system.entity.User;
import com.javaweb.system.query.UserQuery;
import com.javaweb.system.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台用户管理表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 获取用户列表
     *
     * @param userQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:user:index")
    @GetMapping("/index")
    public JsonResult index(UserQuery userQuery) {
        return userService.getList(userQuery);
    }

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return
     */
//    @RequiresPermissions("sys:user:detail")
    @GetMapping("/info/{userId}")
    public JsonResult info(@PathVariable("userId") Integer userId) {
        return userService.info(userId);
    }

    /**
     * 添加用户
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:user:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody User entity) {
        return userService.edit(entity);
    }

    /**
     * 编辑用户
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:user:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody User entity) {
        return userService.edit(entity);
    }

    /**
     * 删除用户
     *
     * @param userIds 用户ID
     * @return
     */
    @Log(title = "用户管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:user:delete")
    @DeleteMapping("/delete/{userIds}")
    public JsonResult delete(@PathVariable("userIds") Integer[] userIds) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return userService.deleteByIds(userIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:user:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody User entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return userService.setStatus(entity);
    }

    /**
     * 重置密码
     *
     * @param resetPwdDto 参数
     * @return
     */
    @Log(title = "用户管理", logType = LogType.RESETPWD)
    @RequiresPermissions("sys:user:resetPwd")
    @PutMapping("/resetPwd")
    public JsonResult resetPwd(@RequestBody ResetPwdDto resetPwdDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return userService.resetPwd(resetPwdDto);
    }

}
