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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrImage {

    /**
     * 二维码的内容(非空)
     */
    private String qrCodeContent;

    /**
     * 二维码的宽度(非空)
     */
    private Integer qrCodeWidth;

    /**
     * 二维码的高度(非空)
     */
    private Integer qrCodeHeight;

    /**
     * 二维码内嵌图片的文件路径(为空则表示:二维码中间不嵌套图片)
     */
    private String embeddedImgFilePath;

    /**
     * 文字的大小(即:正方形文字的长度、宽度)(非空)
     */
    private Integer wordSize;

    /**
     * 文字的内容(非空)
     */
    private String wordContent;

    /**
     * 二维码文件的输出路径(非空)
     */
    private String qrCodeFileOutputPath;

}
