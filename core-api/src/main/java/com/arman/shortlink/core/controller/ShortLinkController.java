package com.arman.shortlink.core.controller;

import com.arman.shortlink.common.convention.R;
import com.arman.shortlink.core.dto.req.CreateShortLinkReq;
import com.arman.shortlink.core.dto.resp.CreateShortLinkResp;
import com.arman.shortlink.core.service.IShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arman
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/short-link")
public class ShortLinkController {

    private final IShortLinkService shortLinkService;

    @PostMapping
    public R<CreateShortLinkResp> createShortLink(@RequestBody CreateShortLinkReq req) {
        return R.ok(shortLinkService.createShortLink(req));
    }

}
