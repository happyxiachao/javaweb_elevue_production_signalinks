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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.DateUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.entity.Menu;
import com.javaweb.system.mapper.MenuMapper;
import com.javaweb.system.query.MenuQuery;
import com.javaweb.system.service.IMenuService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.vo.menu.MenuInfoVo;
import com.javaweb.system.vo.menu.MenuListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取菜单列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        MenuQuery menuQuery = (MenuQuery) query;
        // 查询条件
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
//        // 上级ID
//        if (StringUtils.isNotNull(menuQuery.getPid()) && menuQuery.getPid() > 0) {
//            queryWrapper.eq("pid", menuQuery.getPid());
//        } else {
//            queryWrapper.eq("pid", 0);
//        }
        // 菜单名称
        if (!StringUtils.isEmpty(menuQuery.getTitle())) {
            queryWrapper.like("title", menuQuery.getTitle());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        List<Menu> menuList = menuMapper.selectList(queryWrapper);
        List<MenuListVo> menuListVoList = new ArrayList<>();
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                MenuListVo menuListVo = new MenuListVo();
                BeanUtils.copyProperties(item, menuListVo);
//                // 是否有子级
//                if (item.getType() == 0) {
//                    menuListVo.setHasChildren(true);
//                }
                menuListVoList.add(menuListVo);
            });
        }
        return JsonResult.success(menuListVoList);
    }

    /**
     * 获取菜单详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Menu menu = (Menu) super.getInfo(id);

        // 拷贝属性
        MenuInfoVo menuInfoVo = new MenuInfoVo();
        BeanUtils.copyProperties(menu, menuInfoVo);

        // 获取菜单节点列表
        List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getPid, menu.getId())
                .eq(Menu::getType, 1)
                .eq(Menu::getMark, 1));
        List<Integer> checkedList = new ArrayList<>();
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                checkedList.add(item.getSort());
            });
        }
        menuInfoVo.setCheckedList(checkedList.toArray(new Integer[]{}));
        return menuInfoVo;
    }

    /**
     * 添加或编辑菜单
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Menu entity) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (entity == null) {
            return JsonResult.error("实体对象不存在");
        }
        boolean result = false;
        if (entity.getId() != null && entity.getId() > 0) {
            // 修改记录
            entity.setUpdateUser(ShiroUtils.getUserId());
            entity.setUpdateTime(DateUtils.now());
            result = this.updateById(entity);
        } else {
            // 新增记录
            entity.setCreateUser(ShiroUtils.getUserId());
            entity.setCreateTime(DateUtils.now());
            entity.setMark(1);
            result = this.save(entity);
        }
        if (!result) {
            return JsonResult.error();
        }
        // 权限节点处理
        if (entity.getType() == 0 && !StringUtils.isEmpty(entity.getPath()) && StringUtils.isNotNull(entity.getCheckedList()) && entity.getCheckedList().length > 0) {
            // 删除已有节点
            menuMapper.delete(new LambdaQueryWrapper<Menu>().eq(Menu::getPid, entity.getId()).eq(Menu::getType, 1));
            String[] strings = entity.getPath().split("/");
            // 模块名称
            String moduleName = strings[strings.length - 1];
            // 目标标题
            String moduleTitle = entity.getTitle().replace("管理", "");
            // 遍历权限节点
            for (Integer item : entity.getCheckedList()) {
                Menu menu = new Menu();
                menu.setPid(entity.getId());
                menu.setType(1);
                menu.setStatus(1);
                menu.setHide(0);
                menu.setSort(item);
                menu.setTarget(entity.getTarget());
                menu.setCreateUser(ShiroUtils.getUserId());
                menu.setCreateTime(DateUtils.now());
                if (item.equals(1)) {
                    // 查询
                    menu.setTitle(String.format("查询%s", moduleTitle));
                    menu.setPath(String.format("/%s/index", moduleName));
                    menu.setPermission(String.format("sys:%s:index", moduleName));
                } else if (item.equals(5)) {
                    // 添加
                    menu.setTitle(String.format("添加%s", moduleTitle));
                    menu.setPath(String.format("/%s/add", moduleName));
                    menu.setPermission(String.format("sys:%s:add", moduleName));
                } else if (item.equals(10)) {
                    // 修改
                    menu.setTitle(String.format("修改%s", moduleTitle));
                    menu.setPath(String.format("/%s/edit", moduleName));
                    menu.setPermission(String.format("sys:%s:edit", moduleName));
                } else if (item.equals(15)) {
                    // 删除
                    menu.setTitle(String.format("删除%s", moduleTitle));
                    menu.setPath(String.format("/%s/delete", moduleName));
                    menu.setPermission(String.format("sys:%s:delete", moduleName));
                } else if (item.equals(20)) {
                    // 详情
                    menu.setTitle(String.format("%s详情", moduleTitle));
                    menu.setPath(String.format("/%s/detail", moduleName));
                    menu.setPermission(String.format("sys:%s:detail", moduleName));
                } else if (item.equals(25)) {
                    // 状态
                    menu.setTitle("设置状态");
                    menu.setPath(String.format("/%s/status", moduleName));
                    menu.setPermission(String.format("sys:%s:status", moduleName));
                } else if (item.equals(30)) {
                    // 批量删除
                    menu.setTitle("批量删除");
                    menu.setPath(String.format("/%s/dall", moduleName));
                    menu.setPermission(String.format("sys:%s:dall", moduleName));
                } else if (item.equals(35)) {
                    // 添加子级
                    menu.setTitle("添加子级");
                    menu.setPath(String.format("/%s/addz", moduleName));
                    menu.setPermission(String.format("sys:%s:addz", moduleName));
                } else if (item.equals(40)) {
                    // 全部展开
                    menu.setTitle("全部展开");
                    menu.setPath(String.format("/%s/expand", moduleName));
                    menu.setPermission(String.format("sys:%s:expand", moduleName));
                } else if (item.equals(45)) {
                    // 全部折叠
                    menu.setTitle("全部折叠");
                    menu.setPath(String.format("/%s/collapse", moduleName));
                    menu.setPermission(String.format("sys:%s:collapse", moduleName));
                } else if (item.equals(50)) {
                    // 导出数据
                    menu.setTitle("导出数据");
                    menu.setPath(String.format("/%s/export", moduleName));
                    menu.setPermission(String.format("sys:%s:export", moduleName));
                } else if (item.equals(55)) {
                    // 导入数据
                    menu.setTitle("导入数据");
                    menu.setPath(String.format("/%s/import", moduleName));
                    menu.setPermission(String.format("sys:%s:import", moduleName));
                } else if (item.equals(60)) {
                    // 分配权限
                    menu.setTitle("分配权限");
                    menu.setPath(String.format("/%s/permission", moduleName));
                    menu.setPermission(String.format("sys:%s:permission", moduleName));
                } else if (item.equals(65)) {
                    // 重置密码
                    menu.setTitle("重置密码");
                    menu.setPath(String.format("/%s/resetPwd", moduleName));
                    menu.setPermission(String.format("sys:%s:resetPwd", moduleName));
                }
                // 创建菜单节点
                menuMapper.insert(menu);
            }
        }
        return JsonResult.success(null, "操作成功");
    }

    /**
     * 获取导航菜单
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<Menu> getMenuList(Integer userId) {
        List<Menu> menuList = null;
        if (userId.equals(1)) {
            menuList = getChildrenMenuAll(0);
        } else {
            menuList = menuMapper.getPermissionsListByUserId(userId, 0);
            if (!StringUtils.isNull(menuList)) {
                for (Menu menu : menuList) {
                    List<Menu> childrenList = getChildrenMenuByPid(userId, menu.getId());
                    menu.setChildren(childrenList);
                }
            }
        }
        return menuList;
    }

    /**
     * 根据父级ID获取子级菜单
     *
     * @param pid 上级ID
     * @return
     */
    public List<Menu> getChildrenMenuAll(Integer pid) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        queryWrapper.eq("status", 1);
        // 只取菜单一级
        queryWrapper.eq("type", 0);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<Menu> menuList = list(queryWrapper);
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                List<Menu> childrenList = getChildrenMenuAll(item.getId());
                item.setChildren(childrenList);
            });
        }
        return menuList;
    }

    /**
     * 根据上级ID获取子级菜单
     *
     * @param userId 用户ID
     * @param pid    上级ID
     * @return
     */
    public List<Menu> getChildrenMenuByPid(Integer userId, Integer pid) {
        List<Menu> menuList = menuMapper.getPermissionsListByUserId(userId, pid);
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                List<Menu> childrenList = getChildrenMenuByPid(userId, item.getId());
                item.setChildren(childrenList);
            });
        }
        return menuList;
    }

    /**
     * 获取所有菜单列表
     *
     * @return
     */
    @Override
    public List<Menu> getMenuAll() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.le("type", 2);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<Menu> menuList = list(queryWrapper);
        return menuList;
    }

    /**
     * 获取权限节点列表
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<String> getPermissionList(Integer userId) {
        List<String> permissionList = new ArrayList<>();
        if (userId.equals(1)) {
            // 超级管理员
            List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                    .eq(Menu::getType, 1)
                    .eq(Menu::getStatus, 1));
            if (!menuList.isEmpty()) {
                menuList.forEach(item -> {
                    permissionList.add(item.getPermission());
                });
            }
        } else {
            // 其他
            List<String> stringList = menuMapper.getPermissionList(userId);
            if (!stringList.isEmpty()) {
                for (String permission : stringList) {
                    permissionList.add(permission);
                }
            }
        }
        return permissionList;
    }
}
