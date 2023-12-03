package com.arman.shortlink.admin.controller;

import com.arman.shortlink.admin.common.convention.R;
import com.arman.shortlink.admin.dto.req.GroupSaveReq;
import com.arman.shortlink.admin.service.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 短链接分组管理
 *
 * @author Arman
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/group")
public class GroupController {

    private final IGroupService iGroupService;

    @PostMapping
    public R<?> saveGroup(@RequestBody GroupSaveReq req) {
        iGroupService.saveGroup(req.getGroupName());
        return R.ok();
    }

}
