package com.sdxx.commonutils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "文件响应类", description = "文件上传专用返回响应类，富文本相关")
public class ResponseFile extends R {

    @ApiModelProperty(value = "图片上传成功后的图片地址，放在data返回值中的key常量")
    private static String URL_KEY = "url";

    @ApiModelProperty(value = "图片的文字说明，放在data返回值中的key常量")
    private static String ALT_KEY = "alt";

    @ApiModelProperty(value = "点击图片跳转地址，放在data返回值中的key常量")
    private static String HREF_KEY = "href";

    @ApiModelProperty(value = "错误码")
    private int errno;

    @ApiModelProperty(value = "图片上传成功后的图片地址")
    private String url;

    @ApiModelProperty(value = "图片的文字说明")
    private String alt;

    @ApiModelProperty(value = "点击图片跳转地址（保留，暂未使用）")
    private String href;

    @ApiOperation(value = "新建实例")
    public static ResponseFile newInstance() {
        return new ResponseFile();
    }

    @ApiOperation(value = "操作成功")
    public R success() {
        // 0表示没有错误，其他错误的情况取值为错误码
        this.errno = 0;
        this.data(URL_KEY, url);
        this.data(ALT_KEY, alt);
        this.data(HREF_KEY, href);
        return this;
    }

    @ApiOperation(value = "操作失败，将错误码设置到errno中")
    public R fail(){
        this.errno = this.getCode();
        this.data(URL_KEY, url);
        this.data(ALT_KEY, alt);
        this.data(HREF_KEY, href);
        return this;
    }
}
