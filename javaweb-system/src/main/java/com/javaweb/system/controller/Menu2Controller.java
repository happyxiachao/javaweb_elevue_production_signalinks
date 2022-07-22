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


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javaweb.common.common.BaseController;
import com.javaweb.system.entity.Menu;
import com.javaweb.system.entity.Menu2;
import com.javaweb.system.mapper.Menu2Mapper;
import com.javaweb.system.mapper.MenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/menu2")
public class Menu2Controller extends BaseController {

    @Autowired
    private Menu2Mapper menu2Mapper;
    @Autowired
    private MenuMapper menuMapper;

    @GetMapping("/index")
    public void index() {
        List<Menu2> menu2List = getChildrenMenuAll(0);
        menu2List.forEach(item -> {
            Menu menu = new Menu();
            BeanUtils.copyProperties(item, menu);
            menu.setPid(0);
            menu.setTarget("_self");
            menuMapper.insert(menu);
            if (!item.getChildren().isEmpty()) {
                item.getChildren().forEach(item2 -> {
                    Menu menu2 = new Menu();
                    BeanUtils.copyProperties(item2, menu2);
                    // 设置PID
                    menu2.setPid(menu.getId());
                    menu2.setTarget("_self");
                    menuMapper.insert(menu2);
                    if (!item2.getChildren().isEmpty()) {
                        item2.getChildren().forEach(item3 -> {
                            Menu menu3 = new Menu();
                            BeanUtils.copyProperties(item3, menu3);
                            // 设置PID
                            menu3.setPid(menu2.getId());
                            menu3.setTarget("_self");
                            menuMapper.insert(menu3);
                        });
                    }
                });
            }
        });
    }

    public List<Menu2> getChildrenMenuAll(Integer pid) {
        QueryWrapper<Menu2> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<Menu2> menuList = menu2Mapper.selectList(queryWrapper);
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                List<Menu2> childrenList = getChildrenMenuAll(item.getId());
                item.setChildren(childrenList);
            });
        }
        return menuList;
    }

}
