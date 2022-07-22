package com.javaweb.api.service;

import com.javaweb.api.dto.LoginDto;
import com.javaweb.api.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaweb.common.utils.JsonResult;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-12
 */
public interface IMemberService extends IService<Member> {

    /**
     * 会员登录
     *
     * @param loginDto 参数
     * @return
     */
    JsonResult login(LoginDto loginDto);

}
