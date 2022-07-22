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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javaweb.common.config.CommonConfig;
import com.javaweb.common.utils.CommonUtils;
import com.javaweb.common.utils.JsonResult;
import com.javaweb.common.utils.StringUtils;
import com.javaweb.system.entity.Config;
import com.javaweb.system.entity.ConfigData;
import com.javaweb.system.mapper.ConfigDataMapper;
import com.javaweb.system.mapper.ConfigMapper;
import com.javaweb.system.service.IConfigWebService;
import com.javaweb.system.vo.configweb.ConfigDataInfoVo;
import com.javaweb.system.vo.configweb.ConfigInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConfigWebServiceImpl implements IConfigWebService {

    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private ConfigDataMapper configDataMapper;

    /**
     * 获取配置列表
     *
     * @return
     */
    @Override
    public JsonResult getList() {
        List<Config> configList = configMapper.selectList(new LambdaQueryWrapper<Config>()
                .eq(Config::getMark, 1)
                .orderByAsc(Config::getSort));
        List<ConfigInfoVo> configInfoVoList = new ArrayList<>();
        if (!configList.isEmpty()) {
            for (Config config : configList) {
                ConfigInfoVo configInfoVo = new ConfigInfoVo();
                configInfoVo.setConfigId(config.getId());
                configInfoVo.setConfigName(config.getName());
                // 获取配置项
                List<ConfigData> configDataList = configDataMapper.selectList(new LambdaQueryWrapper<ConfigData>()
                        .eq(ConfigData::getConfigId, config.getId())
                        .eq(ConfigData::getMark, 1)
                        .orderByAsc(ConfigData::getSort));
                List<ConfigDataInfoVo> configDataInfoVoList = new ArrayList<>();
                if (!configDataList.isEmpty()) {
                    configDataList.forEach(item -> {
                        ConfigDataInfoVo configDataInfoVo = new ConfigDataInfoVo();
                        BeanUtils.copyProperties(item, configDataInfoVo);
                        if (item.getType().equals("array") || item.getType().equals("radio") || item.getType().equals("checkbox") || item.getType().equals("select")) {
                            if (StringUtils.isNotEmpty(item.getOptions())) {
                                String[] options = item.getOptions().split("[\\s*|\\t|\\r|\\n{|}]+");
                                if (options.length > 0) {
                                    Map<Integer, String> map = new HashMap<>();
                                    for (String option : options) {
                                        String[] strings1 = option.split("[\\:|\\：]");
                                        map.put(Integer.valueOf(strings1[0]), strings1[1]);
                                    }
                                    configDataInfoVo.setParam(map);
                                }
                            }
                            // 复选框
                            if (item.getType().equals("checkbox")) {
                                String[] strings1 = item.getValue().split(",");
                                configDataInfoVo.setValueList(Arrays.asList(strings1));
                            }
                        }
                        // 单图
                        if (item.getType().equals("image") && StringUtils.isNotEmpty(item.getValue())) {
                            configDataInfoVo.setValue(CommonUtils.getImageURL(item.getValue()));
                        }
                        // 多图
                        if (item.getType().equals("images") && StringUtils.isNotEmpty(item.getValue())) {
                            String[] strings1 = item.getValue().split(",");
                            List<String> stringList = new ArrayList<>();
                            for (String s : strings1) {
                                stringList.add(CommonUtils.getImageURL(s));
                            }
                            configDataInfoVo.setValueList(stringList);
                        }
                        configDataInfoVoList.add(configDataInfoVo);
                    });
                }
                configInfoVo.setDataList(configDataInfoVoList);
                configInfoVoList.add(configInfoVo);
            }
        }
        return JsonResult.success(configInfoVoList);
    }

    /**
     * 保存表单信息
     *
     * @param info 表单信息
     * @return
     */
    @Override
    public JsonResult edit(Map<String, Object> info) {
        if (StringUtils.isNull(info)) {
            return JsonResult.error("表单信息不能为空");
        }
        for (String key : info.keySet()) {
            Object obj = info.get(key);
            if (StringUtils.isNull(obj)) {
                continue;
            }
            String value = "";
            if (obj instanceof List) {
                List<String> stringList = new ArrayList<>();
                for (Object val : ((ArrayList) obj)) {
                    // 图片处理
                    if (val.toString().contains(CommonConfig.imageURL)) {
                        stringList.add(val.toString().replaceAll(CommonConfig.imageURL, ""));
                    } else {
                        stringList.add(val.toString());
                    }
                }
                value = StringUtils.join(stringList, ",");
            } else if (obj.toString().contains("http://") || obj.toString().contains("https://")) {
                // 图片处理
                if (!StringUtils.isEmpty(obj.toString()) && obj.toString().contains(CommonConfig.imageURL)) {
                    value = obj.toString().replaceAll(CommonConfig.imageURL, "");
                } else {
                    value = obj.toString();
                }
            } else {
                value = StringUtils.isNull(obj) ? "" : obj.toString();
            }
            ConfigData configData = configDataMapper.selectOne(new LambdaQueryWrapper<ConfigData>()
                    .eq(ConfigData::getCode, key)
                    .last("limit 1"));
            if (StringUtils.isNull(configData)) {
                continue;
            }
            ConfigData configDataItem = new ConfigData();
            configDataItem.setId(configData.getId());
            configDataItem.setValue(value);
            configDataMapper.updateById(configDataItem);
        }
        return JsonResult.success("保存成功");
    }
}
