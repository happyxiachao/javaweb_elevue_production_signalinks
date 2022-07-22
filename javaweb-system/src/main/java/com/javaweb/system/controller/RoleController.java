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
import com.javaweb.common.enums.LogType;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.system.dto.RolePermissionDto;
import com.javaweb.system.entity.Role;
import com.javaweb.system.query.RoleQuery;
import com.javaweb.system.service.IRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    /**
     * 获取角色列表
     *
     * @param roleQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:role:index")
    @GetMapping("/index")
    public JsonResult index(RoleQuery roleQuery) {
        return roleService.getList(roleQuery);
    }

    /**
     * 添加角色
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "角色管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:role:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Role entity) {
        return roleService.edit(entity);
    }

    /**
     * 编辑角色
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "角色管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:role:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Role entity) {
        return roleService.edit(entity);
    }

    /**
     * 删除角色
     *
     * @param roleIds 角色ID
     * @return
     */
    @Log(title = "角色管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:role:delete")
    @DeleteMapping("/delete/{roleIds}")
    public JsonResult delete(@PathVariable("roleIds") Integer[] roleIds) {
        return roleService.deleteByIds(roleIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "角色管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:role:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody Role entity) {
        return roleService.setStatus(entity);
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @GetMapping("/getRoleList")
    public JsonResult getRoleList() {
        return roleService.getRoleList();
    }

    /**
     * 获取角色菜单列表
     *
     * @param roleId 角色ID
     * @return
     */
    @GetMapping("/getPermissionList/{roleId}")
    public JsonResult getPermissionList(@PathVariable("roleId") Integer roleId) {
        return roleService.getPermissionList(roleId);
    }

    /**
     * 分配角色权限数据
     *
     * @param rolePermissionDto 角色权限集合
     * @return
     */
    @RequiresPermissions("sys:role:permission")
    @PostMapping("/savePermission")
    public JsonResult savePermission(@RequestBody RolePermissionDto rolePermissionDto) {
        return roleService.savePermission(rolePermissionDto);
    }

}
