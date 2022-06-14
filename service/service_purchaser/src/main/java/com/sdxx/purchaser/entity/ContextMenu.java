package com.sdxx.purchaser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="context_menu对象", description="")
public class ContextMenu {

    @ApiModelProperty(value = "id字段唯一")
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    @ApiModelProperty(value = "菜单父id（默认0），0表示根菜单")

    private int parentId;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "默认0，表示第几层菜单")
    private int level;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date changeTime;

    @ApiModelProperty(value = "菜单上的图片")
    private String icon;

    @ApiModelProperty(value = "0:未删除，1:已删除")
    @TableField
    private int IsDelete;

    @ApiModelProperty(value = "菜单排序，相应的值直接录入到表格中。前端不提供相应值的修改。。")
    private int order;

    @ApiModelProperty(value = "vue 路由跳转路径")
    private String path_url;
}
