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


import com.javaweb.admin.entity.MemberLevel;
import com.javaweb.admin.query.MemberLevelQuery;
import com.javaweb.admin.service.IMemberLevelService;
import com.javaweb.common.annotation.Log;
import com.javaweb.common.common.BaseController;
import com.javaweb.common.enums.LogType;
import com.javaweb.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员级别表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-04
 */
@RestController
@RequestMapping("/memberlevel")
public class MemberLevelController extends BaseController {

    @Autowired
    private IMemberLevelService memberLevelService;

    /**
     * 获取会员等级列表
     *
     * @param memberLevelQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:memberlevel:index")
    @GetMapping("/index")
    public JsonResult index(MemberLevelQuery memberLevelQuery) {
        return memberLevelService.getList(memberLevelQuery);
    }

    /**
     * 添加会员等级
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "会员等级", logType = LogType.INSERT)
    @RequiresPermissions("sys:memberlevel:add")
    @PostMapping("/add")
    public JsonResult add(@RequestBody MemberLevel entity) {
        return memberLevelService.edit(entity);
    }

    /**
     * 编辑会员等级
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "会员等级", logType = LogType.UPDATE)
    @RequiresPermissions("sys:memberlevel:edit")
    @PutMapping("/edit")
    public JsonResult edit(@RequestBody MemberLevel entity) {
        return memberLevelService.edit(entity);
    }

    /**
     * 删除会员等级
     *
     * @param memberLevelIds 会员ID
     * @return
     */
    @Log(title = "会员等级", logType = LogType.DELETE)
    @RequiresPermissions("sys:memberlevel:delete")
    @DeleteMapping("/delete/{memberLevelIds}")
    public JsonResult delete(@PathVariable("memberLevelIds") Integer[] memberLevelIds) {
        return memberLevelService.deleteByIds(memberLevelIds);
    }

    /**
     * 获取会员等级列表
     *
     * @return
     */
    @GetMapping("/getMemberLevelList")
    public JsonResult getMemberLevelList() {
        return memberLevelService.getMemberLevelList();
    }

}
