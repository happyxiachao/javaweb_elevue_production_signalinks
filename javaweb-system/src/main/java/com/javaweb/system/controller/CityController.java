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
import com.javaweb.system.entity.City;
import com.javaweb.system.query.CityQuery;
import com.javaweb.system.service.ICityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 高德城市表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/city")
public class CityController extends BaseController {

    @Autowired
    private ICityService cityService;

    /**
     * 获取城市列表
     *
     * @param cityQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:city:index")
    @GetMapping("/index")
    public JsonResult index(CityQuery cityQuery) {
        return cityService.getList(cityQuery);
    }

    /**
     * 添加城市
     *
     * @param entity 查询条件
     * @return
     */
    @Log(title = "城市管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:city:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody City entity) {
        return cityService.add(entity);
    }

    /**
     * 编辑城市
     *
     * @param entity 查询条件
     * @return
     */
    @Log(title = "城市管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:city:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody City entity) {
        return cityService.edit(entity);
    }

    /**
     * 删除城市
     *
     * @param cityId 城市ID
     * @return
     */
    @Log(title = "城市管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:city:delete")
    @DeleteMapping("/delete/{cityId}")
    public JsonResult delete(@PathVariable("cityId") Integer cityId) {
        return cityService.deleteById(cityId);
    }

}
