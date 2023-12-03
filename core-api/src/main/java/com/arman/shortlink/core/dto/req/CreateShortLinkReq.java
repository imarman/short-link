package com.arman.shortlink.core.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Arman
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateShortLinkReq {

    /**
     * 域名
     */
    @NotBlank(message = "域名不能为空")
    private String domain;

    /**
     * 原始链接
     */
    @NotBlank(message = "原始链接不能为空")
    private String originUrl;

    /**
     * 分组标识
     */
    @NotBlank(message = "分组标识不能为空")
    private String gid;

    /**
     * 网站图标
     */
    private String favicon;

    /**
     * 启用标识 0：启用 1：未启用
     */
    private Integer enableStatus;

    /**
     * 创建类型 0：接口创建 1：控制台创建
     */
    @NotNull(message = "创建类型不能为空")
    private Integer createdType;

    /**
     * 有效期类型 0：永久有效 1：自定义
     */
    @NotNull(message = "有效期类型不能为空")
    private Integer validDateType;

    /**
     * 有效期
     */
    private Date validDate;

    /**
     * 描述
     */
    private String describe;

}
