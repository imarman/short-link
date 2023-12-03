package com.arman.shortlink.core.controller;

import com.arman.shortlink.core.service.IShortLinkService;
import lombok.RequiredArgsConstructor;
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


}
