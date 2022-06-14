package com.sdxx.purchaser.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.aspectj.weaver.ast.Test;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "content对象", description = "")
public class Content {

    @ApiModelProperty(value = "菜单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "内容标题")
    private String title;

    @ApiModelProperty(value = "正文")
    private Test content;

    @ApiModelProperty(value = "发布来源")
    private String source;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date changeTime;

    @ApiModelProperty(value = "缩略图，封面url")
    private String coverImg;

    @ApiModelProperty(value = "附件url")
    private String attachment;

    @ApiModelProperty(value = "附件名称")
    private String attachmentName;

    @ApiModelProperty(value = "发布人id。和用户表进行关联。")
    private Long ownerId;

    @ApiModelProperty(value = "0:未删除，1:已删除")
    @TableField
    private Integer IsDelete;

    @ApiModelProperty(value = "菜单id。和菜单表进行关联。")
    private Long contextMenuId;
}
