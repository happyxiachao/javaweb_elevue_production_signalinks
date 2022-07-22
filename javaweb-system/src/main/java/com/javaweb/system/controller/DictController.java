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
import com.javaweb.system.entity.Dict;
import com.javaweb.system.query.DictQuery;
import com.javaweb.system.service.IDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-01
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {

    @Autowired
    private IDictService dictService;

    /**
     * 获取字典列表
     *
     * @param dictQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:dictionary:index")
    @GetMapping("/index")
    public JsonResult index(DictQuery dictQuery) {
        return dictService.getList(dictQuery);
    }

    /**
     * 添加字典
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "字典分组", logType = LogType.INSERT)
    @RequiresPermissions("sys:dictionary:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Dict entity) {
        return dictService.edit(entity);
    }

    /**
     * 编辑字典
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "字典分组", logType = LogType.UPDATE)
    @RequiresPermissions("sys:dictionary:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Dict entity) {
        return dictService.edit(entity);
    }

    /**
     * 删除字典
     *
     * @param dicTypeId 字典ID
     * @return
     */
    @Log(title = "字典分组", logType = LogType.DELETE)
    @RequiresPermissions("sys:dictionary:delete")
    @DeleteMapping("/delete/{dicTypeId}")
    public JsonResult delete(@PathVariable("dicTypeId") Integer dicTypeId) {
        return dictService.deleteById(dicTypeId);
    }

}
