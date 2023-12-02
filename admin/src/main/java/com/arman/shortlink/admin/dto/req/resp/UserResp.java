package com.arman.shortlink.admin.dto.req.resp;

import lombok.Data;

import java.util.Date;

/**
 * @author Arman
 */
@Data
public class UserResp {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;



    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;



}
