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

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.system.dto.ResetPwdDto;
import com.javaweb.system.dto.UpdatePwdDto;
import com.javaweb.system.dto.UpdateUserInfoDto;
import com.javaweb.system.entity.User;
import com.javaweb.system.query.UserQuery;

/**
 * <p>
 * 后台用户管理表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-10-30
 */
public interface IUserService extends IService<User> {

    /**
     * 根据查询条件获取数据列表
     *
     * @param userQuery 查询条件
     * @return
     */
    JsonResult getList(UserQuery userQuery);

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return
     */
    JsonResult info(Integer userId);

    /**
     * 根据实体对象添加、编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult edit(User entity);

    /**
     * 根据ID删除记录
     *
     * @param ids 记录ID
     * @return
     */
    JsonResult deleteByIds(Integer[] ids);

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult setStatus(User entity);

    /**
     * 获取用户信息
     *
     * @return
     */
    JsonResult getUserInfo();

    /**
     * 修改密码
     *
     * @param updatePwdDto 参数
     * @return
     */
    JsonResult updatePwd(UpdatePwdDto updatePwdDto);

    /**
     * 更新个人资料
     *
     * @param updateUserInfoDto 参数
     * @return
     */
    JsonResult updateUserInfo(UpdateUserInfoDto updateUserInfoDto);

    /**
     * 重置密码
     *
     * @param resetPwdDto 参数
     * @return
     */
    JsonResult resetPwd(ResetPwdDto resetPwdDto);

}
