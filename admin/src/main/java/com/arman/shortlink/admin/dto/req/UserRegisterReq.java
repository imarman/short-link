package com.arman.shortlink.admin.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户注册入参
 *
 * @author Arman
 */
@Data
public class UserRegisterReq {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 手机
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;

}
