package com.arman.shortlink.core.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Arman
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "t_link")
public class LinkDo extends BaseDo {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 域名
     */
    @TableField(value = "`domain`")
    private String domain;

    /**
     * 短链接
     */
    @TableField(value = "short_uri")
    private String shortUri;

    /**
     * 完整短链接
     */
    @TableField(value = "full_short_url")
    private String fullShortUrl;

    /**
     * 原始链接
     */
    @TableField(value = "origin_url")
    private String originUrl;

    /**
     * 点击量
     */
    @TableField(value = "click_num")
    private Integer clickNum;

    /**
     * 分组标识
     */
    @TableField(value = "gid")
    private String gid;

    /**
     * 网站图标
     */
    @TableField(value = "favicon")
    private String favicon;

    /**
     * 启用标识 0：启用 1：未启用
     */
    @TableField(value = "enable_status")
    private Boolean enableStatus;

    /**
     * 创建类型 0：接口创建 1：控制台创建
     */
    @TableField(value = "created_type")
    private Boolean createdType;

    /**
     * 有效期类型 0：永久有效 1：自定义
     */
    @TableField(value = "valid_date_type")
    private Boolean validDateType;

    /**
     * 有效期
     */
    @TableField(value = "valid_date")
    private Date validDate;

    /**
     * 描述
     */
    @TableField(value = "`describe`")
    private String describe;

    /**
     * 历史PV
     */
    @TableField(value = "total_pv")
    private Integer totalPv;

    /**
     * 历史UV
     */
    @TableField(value = "total_uv")
    private Integer totalUv;

    /**
     * 历史UIP
     */
    @TableField(value = "total_uip")
    private Integer totalUip;

    /**
     * 删除时间戳
     */
    @TableField(value = "del_time")
    private Long delTime;

}