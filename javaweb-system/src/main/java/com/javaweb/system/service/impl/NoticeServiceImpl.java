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
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.entity.Notice;
import com.javaweb.system.mapper.NoticeMapper;
import com.javaweb.system.query.NoticeQuery;
import com.javaweb.system.service.INoticeService;
import com.javaweb.system.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 通知公告表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-11-07
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 获取通知公告列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        NoticeQuery noticeQuery = (NoticeQuery) query;
        // 查询条件
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        // 通知标题
        if (!StringUtils.isEmpty(noticeQuery.getTitle())) {
            queryWrapper.like("title", noticeQuery.getTitle());
        }
        // 通知状态
        if (StringUtils.isNotNull(noticeQuery.getStatus())) {
            queryWrapper.eq("status", noticeQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询分页数据
        IPage<Notice> page = new Page<>(noticeQuery.getPage(), noticeQuery.getLimit());
        IPage<Notice> pageData = noticeMapper.selectPage(page, queryWrapper);
        return JsonResult.success(pageData);
    }

    /**
     * 获取通知公告详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Notice info = (Notice) super.getInfo(id);
        List<String> stringList = CommonUtils.getImgStr(info.getContent());
        if (stringList.size() > 0) {
            stringList.forEach(item -> {
                info.setContent(info.getContent().replaceAll(item, CommonUtils.getImageURL(item)));
            });
        }
        return info;
    }

    /**
     * 添加或编辑
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Notice entity) {
        if (StringUtils.isNotNull(entity.getId()) && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        // 处理富文本信息
        entity.setContent(entity.getContent().replaceAll(CommonConfig.imageURL, ""));
        return super.edit(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Notice entity) {
        Notice notice = new Notice();
        notice.setId(entity.getId());
        notice.setStatus(entity.getStatus());
        return super.setStatus(notice);
    }

    /**
     * 设置是否置顶
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setIsTop(Notice entity) {
        Notice notice = new Notice();
        notice.setId(entity.getId());
        notice.setIsTop(entity.getIsTop());
        return super.setStatus(notice);
    }
}
