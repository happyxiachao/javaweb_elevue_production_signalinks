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
import com.javaweb.system.entity.Article;
import com.javaweb.system.query.ArticleQuery;
import com.javaweb.system.service.IArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章管理表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-11
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Autowired
    private IArticleService articleService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:article:index")
    @GetMapping("/index")
    public JsonResult index(ArticleQuery query) {
        return articleService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "文章管理表", logType = LogType.INSERT)
    @RequiresPermissions("sys:article:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody Article entity) {
        return articleService.edit(entity);
    }

    /**
     * 获取详情
     *
     * @param articleId 记录ID
     * @return
     */
    @GetMapping("/info/{articleId}")
    public JsonResult info(@PathVariable("articleId") Integer articleId) {
        return articleService.info(articleId);
    }

    /**
     * 更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "文章管理表", logType = LogType.UPDATE)
    @RequiresPermissions("sys:article:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody Article entity) {
        return articleService.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param articleIds 记录ID
     * @return
     */
    @Log(title = "文章管理表", logType = LogType.DELETE)
    @RequiresPermissions("sys:article:delete")
    @DeleteMapping("/delete/{articleIds}")
    public JsonResult delete(@PathVariable("articleIds") Integer[] articleIds) {
        return articleService.deleteByIds(articleIds);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "文章管理表", logType = LogType.STATUS)
    @RequiresPermissions("sys:article:status")
    @PutMapping("/setStatus")
    public JsonResult setStatus(@RequestBody Article entity) {
        return articleService.setStatus(entity);
    }
}