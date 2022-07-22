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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 文件上传配置
 */
@Configuration
@Data
public class UploadFileConfig {
    /**
     * 上传目录
     */
    public static String uploadFolder;
    /**
     * 访问路径
     */
    public static String staticAccessPath;
    /**
     * 上传服务器的映射文件夹
     */
    public static String accessPath;

    @Value("${file.uploadFolder}")
    public void setUploadFolder(String path) {
        uploadFolder = path;
    }

    @Value("${file.staticAccessPath}")
    public void setStaticAccessPath(String path) {
        staticAccessPath = path;
    }

    @Value("${file.accessPath}")
    public void setAccessPath(String path) {
        accessPath = path;
    }
}
