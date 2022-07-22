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
import com.javaweb.common.common.BaseQuery;
import com.javaweb.common.common.BaseServiceImpl;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.entity.Item;
import com.javaweb.system.entity.ItemCate;
import com.javaweb.system.mapper.ItemCateMapper;
import com.javaweb.system.mapper.ItemMapper;
import com.javaweb.system.query.ItemCateQuery;
import com.javaweb.system.service.IItemCateService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.vo.itemcate.ItemCateListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 栏目管理表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Service
public class ItemCateServiceImpl extends BaseServiceImpl<ItemCateMapper, ItemCate> implements IItemCateService {

    @Autowired
    private ItemCateMapper itemCateMapper;
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 获取栏目列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ItemCateQuery itemCateQuery = (ItemCateQuery) query;
        // 查询条件
        QueryWrapper<ItemCate> queryWrapper = new QueryWrapper<>();
        // 栏目名称
        if (!StringUtils.isEmpty(itemCateQuery.getName())) {
            queryWrapper.like("name", itemCateQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");

        // 查询分页数据
        List<ItemCate> pageData = itemCateMapper.selectList(queryWrapper);
        List<ItemCateListVo> itemCateListVoList = new ArrayList<>();
        pageData.forEach(x -> {
            ItemCateListVo itemCateListVo = new ItemCateListVo();
            BeanUtils.copyProperties(x, itemCateListVo);
            // 封面
            itemCateListVo.setCover(CommonUtils.getImageURL(x.getCover()));
            // 所属站点
            if (StringUtils.isNotNull(x.getItemId())) {
                Item item = itemMapper.selectById(x.getItemId());
                if (item != null) {
                    itemCateListVo.setItemName(item.getName());
                }
            }
            itemCateListVoList.add(itemCateListVo);
        });
        return JsonResult.success(itemCateListVoList);
    }

    /**
     * 添加或编辑栏目
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(ItemCate entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        // 图片处理
        if (!StringUtils.isEmpty(entity.getCover()) && entity.getCover().contains(CommonConfig.imageURL)) {
            entity.setCover(entity.getCover().replaceAll(CommonConfig.imageURL, ""));
        }
        return super.edit(entity);
    }

    /**
     * 获取栏目名称
     *
     * @param cateId    栏目ID
     * @param delimiter 分隔符
     * @return
     */
    @Override
    public String getCateName(Integer cateId, String delimiter) {
        List<String> nameList = new ArrayList<>();
        while (cateId > 0) {
            ItemCate cateInfo = itemCateMapper.selectById(cateId);
            if (cateInfo != null) {
                nameList.add(cateInfo.getName());
                cateId = cateInfo.getPid();
            } else {
                cateId = 0;
            }
        }
        // 使用集合工具实现数组翻转
        Collections.reverse(nameList);
        return org.apache.commons.lang3.StringUtils.join(nameList.toArray(), delimiter);
    }

    /**
     * 获取栏目列表
     *
     * @return
     */
    @Override
    public List<ItemCate> getCateList() {
        QueryWrapper<ItemCate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<ItemCate> itemCateList = list(queryWrapper);
        return itemCateList;
    }
}
