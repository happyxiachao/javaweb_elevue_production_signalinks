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

package com.javaweb.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里短信配置类
 */
// 让Spring启动的时候扫描到该类，并添加到Spring容器中
@Configuration
// 设置前缀
@ConfigurationProperties(prefix = "spring.alisms")
@Data
public class AliSmsConfig {

    /**
     * KEY
     */
    private String accessKeyId;

    /**
     * 密钥
     */
    private String accessKeySecret;

    /**
     * 区域ID
     */
    private String regionId;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信模板ID
     */
    private String templateCode;

}
