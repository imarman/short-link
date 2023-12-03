package com.arman.shortlink.core.service;

import com.arman.shortlink.core.dao.pojo.LinkDo;
import com.arman.shortlink.core.dto.req.CreateShortLinkReq;
import com.arman.shortlink.core.dto.resp.CreateShortLinkResp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Arman
 */
public interface IShortLinkService extends IService<LinkDo> {


    /**
     * 生成短链接
     */
    CreateShortLinkResp createShortLink(CreateShortLinkReq req);

}
