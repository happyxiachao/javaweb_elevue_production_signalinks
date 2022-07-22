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
import com.javaweb.system.entity.LayoutDesc;
import com.javaweb.system.query.LayoutDescQuery;
import com.javaweb.system.service.ILayoutDescService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 布局描述管理 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/layoutdesc")
public class LayoutDescController extends BaseController {

    @Autowired
    private ILayoutDescService layoutDescService;

    /**
     * 获取布局描述列表
     *
     * @param layoutDescQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:layoutdesc:index")
    @GetMapping("/index")
    public JsonResult index(LayoutDescQuery layoutDescQuery) {
        return layoutDescService.getList(layoutDescQuery);
    }

    /**
     * 添加布局描述
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "布局描述", logType = LogType.INSERT)
    @RequiresPermissions("sys:layoutdesc:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody LayoutDesc entity) {
        return layoutDescService.edit(entity);
    }

    /**
     * 编辑布局描述
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "布局描述", logType = LogType.UPDATE)
    @RequiresPermissions("sys:layoutdesc:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody LayoutDesc entity) {
        return layoutDescService.edit(entity);
    }

    /**
     * 删除布局描述
     *
     * @param layoutDescIds 布局描述ID
     * @return
     */
    @Log(title = "布局描述", logType = LogType.DELETE)
    @RequiresPermissions("sys:layoutdesc:delete")
    @DeleteMapping("/delete/{layoutDescIds}")
    public JsonResult delete(@PathVariable("layoutDescIds") Integer[] layoutDescIds) {
        return layoutDescService.deleteByIds(layoutDescIds);
    }

    /**
     * 获取推荐描述列表
     *
     * @return
     */
    @GetMapping("/getLayoutDescList")
    public JsonResult getLayoutDescList() {
        return layoutDescService.getLayoutDescList();
    }

}
