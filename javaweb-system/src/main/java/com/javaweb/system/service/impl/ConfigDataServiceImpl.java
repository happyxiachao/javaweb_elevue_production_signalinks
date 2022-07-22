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

package com.javaweb.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.entity.ConfigData;
import com.javaweb.system.mapper.ConfigDataMapper;
import com.javaweb.system.query.ConfigDataQuery;
import com.javaweb.system.service.IConfigDataService;
import com.javaweb.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-06
 */
@Service
public class ConfigDataServiceImpl extends BaseServiceImpl<ConfigDataMapper, ConfigData> implements IConfigDataService {

    @Autowired
    private ConfigDataMapper configDataMapper;

    /**
     * 获取配置列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ConfigDataQuery configQuery = (ConfigDataQuery) query;
        // 查询条件
        QueryWrapper<ConfigData> queryWrapper = new QueryWrapper<>();
        // 配置ID
        if (StringUtils.isNotNull(configQuery.getConfigId())) {
            queryWrapper.eq("config_id", configQuery.getConfigId());
        }
        // 配置标题
        if (!StringUtils.isEmpty(configQuery.getTitle())) {
            queryWrapper.like("title", configQuery.getTitle());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<ConfigData> page = new Page<>(configQuery.getPage(), configQuery.getLimit());
        IPage<ConfigData> pageData = configDataMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(ConfigData entity) {
        ConfigData config = new ConfigData();
        config.setId(entity.getId());
        config.setStatus(entity.getStatus());
        config.setUpdateUser(ShiroUtils.getUserId());
        return super.setStatus(config);
    }

}
