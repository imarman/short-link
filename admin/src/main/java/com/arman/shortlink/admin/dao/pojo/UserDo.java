package com.arman.shortlink.admin.dao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author Arman
 */
@Data
@TableName(value = "t_user")
public class UserDo {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 手机
     */
    @TableField(value = "mibile")
    private String mibile;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 注销时间
     */
    @TableField(value = "deletion_time")
    private Long deletionTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 删除表是(0:未删除，1:已删除)
     */
    @TableField(value = "del_flag")
    @TableLogic(value = "1", delval = "0")
    private Boolean delFlag;

}