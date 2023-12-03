package com.arman.shortlink.admin.dao.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author Arman
 */
@Data
@TableName(value = "t_group")
public class GroupDo {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 分组标识
     */
    @TableField(value = "gid")
    private String gid;

    /**
     * 分组名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 创建分组用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 分组排序
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除表是(0:未删除，1:已删除)
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean delFlag;
}