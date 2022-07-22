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

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.entity.Item;
import com.javaweb.system.entity.Layout;
import com.javaweb.system.entity.LayoutDesc;
import com.javaweb.system.mapper.ItemMapper;
import com.javaweb.system.mapper.LayoutDescMapper;
import com.javaweb.system.mapper.LayoutMapper;
import com.javaweb.system.query.LayoutQuery;
import com.javaweb.system.service.ILayoutService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.vo.layout.LayoutListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 页面布局管理 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Service
public class LayoutServiceImpl extends BaseServiceImpl<LayoutMapper, Layout> implements ILayoutService {

    @Autowired
    private LayoutMapper layoutMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private LayoutDescMapper layoutDescMapper;

    /**
     * 获取布局描述
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        LayoutQuery layoutQuery = (LayoutQuery) query;
        // 查询条件
        QueryWrapper<Layout> queryWrapper = new QueryWrapper<>();
        // 站点ID
        if (StringUtils.isNotNull(layoutQuery.getItemId())) {
            queryWrapper.like("item_id", layoutQuery.getItemId());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        IPage<Layout> page = new Page<>(layoutQuery.getPage(), layoutQuery.getLimit());
        IPage<Layout> pageData = layoutMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            LayoutListVo layoutListVo = Convert.convert(LayoutListVo.class, x);
            // 站点名称
            if (StringUtils.isNotNull(x.getItemId())) {
                Item item = itemMapper.selectById(x.getItemId());
                if (item != null) {
                    layoutListVo.setItemName(item.getName());
                }
            }
            // 图片处理
            if (!StringUtils.isEmpty(x.getImage())) {
                layoutListVo.setImage(CommonUtils.getImageURL(x.getImage()));
            }
            // 布局位置描述
            if (x.getLocDescId() > 0) {
                LayoutDesc layoutDesc = layoutDescMapper.selectById(x.getLocDescId());
                if (layoutDesc != null) {
                    layoutListVo.setLocDesc(String.format("%s => %s", layoutDesc.getLocDesc(), layoutDesc.getLocId()));
                }
            }
            return layoutListVo;
        });
        return JsonResult.success(pageData);
    }

    /**
     * 获取布局详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Layout layout = (Layout) super.getInfo(id);
        // 图片处理
        if (!StringUtils.isEmpty(layout.getImage())) {
            layout.setImage(CommonUtils.getImageURL(layout.getImage()));
        }
        return layout;
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Layout entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        // 图片处理
        if (!StringUtils.isEmpty(entity.getImage()) && entity.getImage().contains(CommonConfig.imageURL)) {
            entity.setImage(entity.getImage().replaceAll(CommonConfig.imageURL, ""));
        }
        return super.edit(entity);
    }
}
