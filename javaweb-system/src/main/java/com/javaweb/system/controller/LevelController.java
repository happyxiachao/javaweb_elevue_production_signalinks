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
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.enums.LogType;
import com.javaweb.common.utils.ExcelUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.system.entity.Level;
import com.javaweb.system.query.LevelQuery;
import com.javaweb.system.service.ILevelService;
import com.javaweb.system.vo.level.LevelInfoVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 职级表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-02
 */
@RestController
@RequestMapping("/level")
public class LevelController extends BaseController {

    @Autowired
    private ILevelService levelService;

    /**
     * 获取职级列表
     *
     * @param levelQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:level:index")
    @GetMapping("/index")
    public JsonResult index(LevelQuery levelQuery) {
        return levelService.getList(levelQuery);
    }

    /**
     * 添加职级
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "职级管理", logType = LogType.INSERT)
    @RequiresPermissions("sys:level:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Level entity) {
        return levelService.edit(entity);
    }

    /**
     * 编辑职级
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "职级管理", logType = LogType.UPDATE)
    @RequiresPermissions("sys:level:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Level entity) {
        return levelService.edit(entity);
    }

    /**
     * 删除职级
     *
     * @param levelIds 职级ID
     * @return
     */
    @Log(title = "职级管理", logType = LogType.DELETE)
    @RequiresPermissions("sys:level:delete")
    @DeleteMapping("/delete/{levelIds}")
    public JsonResult delete(@PathVariable("levelIds") Integer[] levelIds) {
        return levelService.deleteByIds(levelIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "职级管理", logType = LogType.STATUS)
    @RequiresPermissions("sys:level:status")
    @PutMapping("/status")
    public JsonResult status(@RequestBody Level entity) {
        return levelService.setStatus(entity);
    }

    /**
     * 获取职级列表
     *
     * @return
     */
    @GetMapping("/getLevelList")
    public JsonResult getLevelList() {
        return levelService.getLevelList();
    }

    /**
     * 导入Excel
     *
     * @param request 网络请求
     * @return
     */
    @Log(title = "职级管理", logType = LogType.IMPORT)
    @RequiresPermissions("sys:level:import")
    @PostMapping("/importExcel/{name}")
    public JsonResult importExcel(HttpServletRequest request, @PathVariable("name") String name) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        return levelService.importExcel(request, name);
    }

    /**
     * 导出Excel
     *
     * @param levelQuery 查询条件
     * @return
     */
    @PostMapping("/exportExcel")
    public JsonResult exportExcel(@RequestBody LevelQuery levelQuery) {
        List<LevelInfoVo> levelInfoVoList = levelService.exportExcel(levelQuery);
        ExcelUtils<LevelInfoVo> excelUtils = new ExcelUtils<LevelInfoVo>(LevelInfoVo.class);
        return excelUtils.exportExcel(levelInfoVoList, "职级列表");
    }

}
