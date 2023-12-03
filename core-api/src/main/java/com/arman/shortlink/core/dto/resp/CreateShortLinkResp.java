package com.arman.shortlink.core.dto.resp;

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
public class CreateShortLinkResp {

    /**
     * 分组id
     */
    private String gid;


    /**
     * 短链接
     */
    private String shortUri;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 原始链接
     */
    private String originUrl;

}
