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

package com.javaweb.system.service;

import com.javaweb.common.common.IBaseService;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.system.entity.Level;
import com.javaweb.system.query.LevelQuery;
import com.javaweb.system.vo.level.LevelInfoVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 职级表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-02
 */
public interface ILevelService extends IBaseService<Level> {

    /**
     * 获取职级列表
     *
     * @return
     */
    JsonResult getLevelList();

    /**
     * 导入Excel数据
     *
     * @param request 网络请求
     * @param name    目录名称
     * @return
     */
    JsonResult importExcel(HttpServletRequest request, String name);

    /**
     * 导出Excel数据
     *
     * @param levelQuery 查询条件
     * @return
     */
    List<LevelInfoVo> exportExcel(LevelQuery levelQuery);

}
