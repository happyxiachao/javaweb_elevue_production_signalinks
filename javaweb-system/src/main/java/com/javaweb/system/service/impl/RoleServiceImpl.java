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

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.dto.RolePermissionDto;
import com.javaweb.system.entity.Menu;
import com.javaweb.system.entity.Role;
import com.javaweb.system.entity.RoleMenu;
import com.javaweb.system.mapper.RoleMapper;
import com.javaweb.system.mapper.RoleMenuMapper;
import com.javaweb.system.query.RoleQuery;
import com.javaweb.system.service.IMenuService;
import com.javaweb.system.service.IRoleMenuService;
import com.javaweb.system.service.IRoleService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.vo.role.RoleListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-31
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private IRoleMenuService roleMenuService;

    /**
     * 获取角色列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        RoleQuery roleQuery = (RoleQuery) query;
        // 查询条件
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        // 角色名称
        if (!StringUtils.isEmpty(roleQuery.getName())) {
            queryWrapper.like("name", roleQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<Role> page = new Page<>(roleQuery.getPage(), roleQuery.getLimit());
        IPage<Role> pageData = roleMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            RoleListVo roleListVo = Convert.convert(RoleListVo.class, x);
            // TODO...
            return roleListVo;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Role entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        return super.edit(entity);
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @Override
    public JsonResult getRoleList() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        return JsonResult.success(list(queryWrapper));
    }

    /**
     * 获取菜单列表
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    public JsonResult getPermissionList(Integer roleId) {
        List<Menu> menuList = menuService.getMenuAll();
        if (!menuList.isEmpty()) {
            // 获取角色菜单列表
            QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            queryWrapper.select("menu_id");
            List<RoleMenu> roleMenuList = roleMenuMapper.selectList(queryWrapper);
            List<Integer> menuIds = roleMenuList.stream().map(p -> p.getMenuId()).collect(Collectors.toList());
            menuList.forEach(item -> {
                if (menuIds.contains(item.getId())) {
                    item.setChecked(true);
                    item.setOpen(true);
                }
            });
        }
        return JsonResult.success(menuList);
    }

    /**
     * 分配角色菜单权限数据
     *
     * @param rolePermissionDto 角色权限集合
     * @return
     */
    @Override
    public JsonResult savePermission(RolePermissionDto rolePermissionDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        // 角色ID校验
        if (StringUtils.isNull(rolePermissionDto.getRoleId())) {
            return JsonResult.error("角色ID不能位空");
        }
        // 同步删除角色菜单关系数据
        roleMenuService.deleteRoleMenus(rolePermissionDto.getRoleId());
        // 插入新的角色菜单关系数据
        List<RoleMenu> roleMenuList = new ArrayList<>();
        if (!StringUtils.isEmpty(rolePermissionDto.getMenuIds())) {
            for (String menuId : rolePermissionDto.getMenuIds()) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(rolePermissionDto.getRoleId());
                roleMenu.setMenuId(Integer.valueOf(menuId));
                roleMenuList.add(roleMenu);
            }
        }
        // 批量插入角色菜单关系数据
        roleMenuService.saveBatch(roleMenuList);
        return JsonResult.success("权限保存成功");
    }
}
