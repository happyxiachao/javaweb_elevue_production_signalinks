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
import com.javaweb.common.utils.DateUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.constant.ArticleConstant;
import com.javaweb.system.entity.Article;
import com.javaweb.system.mapper.ArticleMapper;
import com.javaweb.system.query.ArticleQuery;
import com.javaweb.system.service.IArticleService;
import com.javaweb.system.service.IItemCateService;
import com.javaweb.system.utils.ShiroUtils;
import com.javaweb.system.vo.article.ArticleInfoVo;
import com.javaweb.system.vo.article.ArticleListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章管理表 服务类实现
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-10-11
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private IItemCateService itemCateService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ArticleQuery articleQuery = (ArticleQuery) query;
        // 查询条件
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        // 文章标题
        if (!StringUtils.isEmpty(articleQuery.getTitle())) {
            queryWrapper.like("title", articleQuery.getTitle());
        }
        // 状态：1正常 2停用
        if (!StringUtils.isEmpty(articleQuery.getStatus())) {
            queryWrapper.eq("status", articleQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 获取数据列表
        IPage<Article> page = new Page<>(articleQuery.getPage(), articleQuery.getLimit());
        IPage<Article> pageData = articleMapper.selectPage(page, queryWrapper);
        pageData.convert(x -> {
            ArticleListVo articleListVo = Convert.convert(ArticleListVo.class, x);
            // 文章封面
            if (!StringUtils.isEmpty(articleListVo.getCover())) {
                articleListVo.setCover(CommonUtils.getImageURL(articleListVo.getCover()));
            }
            // 状态描述
            if (articleListVo.getStatus() != null && articleListVo.getStatus() > 0) {
                articleListVo.setStatusName(ArticleConstant.ARTICLE_STATUS_LIST.get(articleListVo.getStatus()));
            }
            // 所属栏目
            if (x.getCateId() > 0) {
                String cateName = itemCateService.getCateName(x.getCateId(), ">>");
                articleListVo.setCateName(cateName);
            }
            return articleListVo;
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
        Article entity = (Article) super.getInfo(id);
        // 返回视图Vo
        ArticleInfoVo articleInfoVo = new ArticleInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, articleInfoVo);
        // 文章封面
        if (!StringUtils.isEmpty(articleInfoVo.getCover())) {
            articleInfoVo.setCover(CommonUtils.getImageURL(articleInfoVo.getCover()));
        }
        // 图集
        if (StringUtils.isNotEmpty(entity.getImgs())) {
            List<String> stringList = new ArrayList<>();
            String[] strings = entity.getImgs().split(",");
            for (String string : strings) {
                stringList.add(CommonUtils.getImageURL(string));
            }
            articleInfoVo.setImgsList(stringList.toArray(new String[stringList.size()]));
        }
        // 富文本图片
        List<String> stringList = CommonUtils.getImgStr(entity.getContent());
        if (stringList.size() > 0) {
            stringList.forEach(item -> {
                articleInfoVo.setContent(entity.getContent().replaceAll(item, CommonUtils.getImageURL(item)));
            });
        }
        return articleInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Article entity) {
        // 文章封面
        if (entity.getCover().contains(CommonConfig.imageURL)) {
            entity.setCover(entity.getCover().replaceAll(CommonConfig.imageURL, ""));
        }
        if (entity.getId() != null && entity.getId() > 0) {
            entity.setUpdateUser(ShiroUtils.getUserId());
        } else {
            entity.setCreateUser(ShiroUtils.getUserId());
        }
        // 处理图集
        if (StringUtils.isNotNull(entity.getImgsList()) && entity.getImgsList().length > 0) {
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < entity.getImgsList().length; i++) {
                stringList.add(entity.getImgsList()[i].replace(CommonConfig.imageURL, ""));
            }
            entity.setImgs(String.join(",", stringList));
        }
        // 处理富文本
        entity.setContent(entity.getContent().replaceAll(CommonConfig.imageURL, ""));
        return super.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult delete(Article entity) {
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
    public JsonResult setStatus(Article entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

}