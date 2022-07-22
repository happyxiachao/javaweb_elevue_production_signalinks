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

package com.javaweb.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.admin.constant.Example2Constant;
import com.javaweb.admin.entity.Example2;
import com.javaweb.admin.mapper.Example2Mapper;
import com.javaweb.admin.query.Example2Query;
import com.javaweb.admin.service.IExample2Service;
import com.javaweb.admin.vo.example2.Example2InfoVo;
import com.javaweb.admin.vo.example2.Example2ListVo;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.DateUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.utils.ShiroUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
  * <p>
  * 演示案例二 服务类实现
  * </p>
  *
  * @author 鲲鹏
  * @since 2021-10-12
  */
@Service
public class Example2ServiceImpl extends BaseServiceImpl<Example2Mapper, Example2> implements IExample2Service {

    @Autowired
    private Example2Mapper example2Mapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        Example2Query example2Query = (Example2Query) query;
        // 查询条件
        QueryWrapper<Example2> queryWrapper = new QueryWrapper<>();
        // 案例名称
        if (!StringUtils.isEmpty(example2Query.getName())) {
            queryWrapper.like("name", example2Query.getName());
        }
        // 类型：1京东 2淘宝 3拼多多 4唯品会
        if (!StringUtils.isEmpty(example2Query.getType())) {
            queryWrapper.eq("type", example2Query.getType());
        }
        // 状态：1正常 2停用
        if (!StringUtils.isEmpty(example2Query.getStatus())) {
            queryWrapper.eq("status", example2Query.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<Example2> page = new Page<>(example2Query.getPage(), example2Query.getLimit());
        IPage<Example2> pageData = example2Mapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            Example2ListVo example2ListVo = Convert.convert(Example2ListVo.class, x);
            // 头像地址
            if (!StringUtils.isEmpty(example2ListVo.getAvatar())) {
                example2ListVo.setAvatar(CommonUtils.getImageURL(example2ListVo.getAvatar()));
            }
            // 类型描述
            if (example2ListVo.getType() != null && example2ListVo.getType() > 0) {
                example2ListVo.setTypeName(Example2Constant.EXAMPLE2_TYPE_LIST.get(example2ListVo.getType()));
            }
            // 状态描述
            if (example2ListVo.getStatus() != null && example2ListVo.getStatus() > 0) {
                example2ListVo.setStatusName(Example2Constant.EXAMPLE2_STATUS_LIST.get(example2ListVo.getStatus()));
            }
            return example2ListVo;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 获取详情Vo
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Example2 entity = (Example2) super.getInfo(id);
        // 返回视图Vo
        Example2InfoVo example2InfoVo = new Example2InfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, example2InfoVo);
        // 头像
        if (!StringUtils.isEmpty(example2InfoVo.getAvatar())) {
            example2InfoVo.setAvatar(CommonUtils.getImageURL(example2InfoVo.getAvatar()));
        }
        return example2InfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Example2 entity) {
        // 头像
        if (entity.getAvatar().contains(CommonConfig.imageURL)) {
            entity.setAvatar(entity.getAvatar().replaceAll(CommonConfig.imageURL, ""));
        }
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
            entity.setUpdateTime(DateUtils.now());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
            entity.setCreateTime(DateUtils.now());
        }
        return super.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult delete(Example2 entity) {
        entity.setUpdateUser(1);
        entity.setUpdateTime(DateUtils.now());
        entity.setMark(0);
        return super.delete(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Example2 entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

}