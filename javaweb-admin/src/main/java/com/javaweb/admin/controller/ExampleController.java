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

package com.javaweb.admin.controller;

import com.javaweb.admin.entity.Example;
import com.javaweb.admin.query.ExampleQuery;
import com.javaweb.admin.service.IExampleService;
import com.javaweb.common.annotation.Log;
import com.javaweb.common.common.BaseController;
import com.javaweb.common.enums.LogType;
import com.javaweb.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 演示案例一 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-12
 */
@RestController
@RequestMapping("/example")
public class ExampleController extends BaseController {

    @Autowired
    private IExampleService exampleService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:example:index")
    @GetMapping("/index")
    public JsonResult index(ExampleQuery query) {
        return exampleService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例一", logType = LogType.INSERT)
    @RequiresPermissions("sys:example:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Example entity) {
        return exampleService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param exampleId 记录ID
     * @return
     */
    @GetMapping("/info/{exampleId}")
    public JsonResult info(@PathVariable("exampleId") Integer exampleId) {
        return exampleService.info(exampleId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例一", logType = LogType.UPDATE)
    @RequiresPermissions("sys:example:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Example entity) {
        return exampleService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param exampleIds 记录ID
     * @return
     */
    @Log(title = "演示案例一", logType = LogType.DELETE)
    @RequiresPermissions("sys:example:delete")
    @DeleteMapping("/delete/{exampleIds}")
    public JsonResult delete(@PathVariable("exampleIds") Integer[] exampleIds) {
        return exampleService.deleteByIds(exampleIds);
    }

    /**
     * 设置是否VIP
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例一", logType = LogType.STATUS)
    @RequiresPermissions("sys:example:isVip")
    @PutMapping("/setIsVip")
    public JsonResult setIsVip(@RequestBody Example entity) {
        return exampleService.setIsVip(entity);
    }
    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例一", logType = LogType.STATUS)
    @RequiresPermissions("sys:example:status")
    @PutMapping("/setStatus")
    public JsonResult setStatus(@RequestBody Example entity) {
        return exampleService.setStatus(entity);
    }
}