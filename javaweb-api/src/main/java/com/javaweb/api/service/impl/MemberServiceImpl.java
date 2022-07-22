package com.javaweb.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javaweb.api.dto.LoginDto;
import com.javaweb.api.entity.Member;
import com.javaweb.api.mapper.MemberMapper;
import com.javaweb.api.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.JwtUtil;
import com.javaweb.common.utils.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.javaweb.common.utils.JwtUtil.parseJWT;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-12
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 会员登录
     *
     * @param loginDto 参数
     * @return
     */
    @Override
    public JsonResult login(LoginDto loginDto) {
        // 会员账号
        if (StringUtils.isEmpty(loginDto.getUsername())) {
            return JsonResult.error("会员账号不能为空");
        }
        // 会员密码
        if (StringUtils.isEmpty(loginDto.getPassword())) {
            return JsonResult.error("会员密码不能为空");
        }
        // 查询用户信息
        Member member = memberMapper.selectOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getUsername, loginDto.getUsername())
                .eq(Member::getMark, 1)
                .last("limit 1"));
        if (StringUtils.isNull(member)) {
            return JsonResult.error("用户不存在");
        }
        // 密码校验
        if (!member.getPassword().equals(CommonUtils.password(loginDto.getPassword()))) {
            return JsonResult.error("用户密码不正确");
        }
        // 用户状态验证
        if (!member.getStatus().equals(1)) {
            return JsonResult.error("您的账号已被禁用");
        }
        // 返回Token
        String token = JwtUtil.createJWT(member.getId());
        System.out.println("token值：" + token);
        // Jwt解密代码如下：
        Claims claims = JwtUtil.parseJWT(token);
        Integer userId = Integer.valueOf(claims.get("id").toString());
        System.out.println("用户ID：" + userId);
        return JsonResult.success(token);
    }
}
