package com.arman.shortlink.admin.controller;

import com.arman.shortlink.admin.service.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接分组管理控制层
 *
 * @author Arman
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/group")
public class GroupController {

    private final IGroupService iGroupService;



}
