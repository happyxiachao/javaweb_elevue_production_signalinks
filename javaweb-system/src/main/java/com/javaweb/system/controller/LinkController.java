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
import com.javaweb.system.entity.Link;
import com.javaweb.system.query.LinkQuery;
import com.javaweb.system.service.ILinkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 友链管理表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/link")
public class LinkController extends BaseController {

    @Autowired
    private ILinkService linkService;

    /**
     * 获取友链列表
     *
     * @param linkQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:link:index")
    @GetMapping("/index")
    public JsonResult index(LinkQuery linkQuery) {
        return linkService.getList(linkQuery);
    }

    /**
     * 添加友链
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "友链管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:link:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Link entity) {
        return linkService.edit(entity);
    }

    /**
     * 编辑友链
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "友链管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:link:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Link entity) {
        return linkService.edit(entity);
    }

    /**
     * 删除友链
     *
     * @param linkIds 友链ID
     * @return
     */
    @Log(title = "友链管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:link:delete")
    @DeleteMapping("/delete/{linkIds}")
    public JsonResult delete(@PathVariable("linkIds") Integer[] linkIds) {
        return linkService.deleteByIds(linkIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "友链管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:link:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody Link entity) {
        return linkService.setStatus(entity);
    }

}
