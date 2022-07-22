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

import com.javaweb.admin.entity.Example2;
import com.javaweb.admin.query.Example2Query;
import com.javaweb.admin.service.IExample2Service;
import com.javaweb.common.annotation.Log;
import com.javaweb.common.common.BaseController;
import com.javaweb.common.enums.LogType;
import com.javaweb.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 演示案例二 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-12
 */
@RestController
@RequestMapping("/example2")
public class Example2Controller extends BaseController {

    @Autowired
    private IExample2Service example2Service;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:example2:index")
    @GetMapping("/index")
    public JsonResult index(Example2Query query) {
        return example2Service.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例二", logType = LogType.INSERT)
    @RequiresPermissions("sys:example2:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Example2 entity) {
        return example2Service.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param example2Id 记录ID
     * @return
     */
    @GetMapping("/info/{example2Id}")
    public JsonResult info(@PathVariable("example2Id") Integer example2Id) {
        return example2Service.info(example2Id);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例二", logType = LogType.UPDATE)
    @RequiresPermissions("sys:example2:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Example2 entity) {
        return example2Service.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param example2Ids 记录ID
     * @return
     */
    @Log(title = "演示案例二", logType = LogType.DELETE)
    @RequiresPermissions("sys:example2:delete")
    @DeleteMapping("/delete/{example2Ids}")
    public JsonResult delete(@PathVariable("example2Ids") Integer[] example2Ids) {
        return example2Service.deleteByIds(example2Ids);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "演示案例二", logType = LogType.STATUS)
    @RequiresPermissions("sys:example2:status")
    @PutMapping("/setStatus")
    public JsonResult setStatus(@RequestBody Example2 entity) {
        return example2Service.setStatus(entity);
    }
}