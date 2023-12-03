package com.arman.shortlink.admin.dao.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

import lombok.*;

/**
 * @author Arman
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "t_group")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDo extends BaseDo {
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

}