package com.arman.shortlink.admin.controller;

import com.arman.shortlink.admin.dto.req.GroupSaveReq;
import com.arman.shortlink.admin.dto.req.GroupSortReq;
import com.arman.shortlink.admin.dto.req.GroupUpdateReq;
import com.arman.shortlink.admin.dto.resp.GroupResp;
import com.arman.shortlink.admin.service.IGroupService;
import com.arman.shortlink.common.convention.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping()
    public R<List<GroupResp>> list() {
        return R.ok(iGroupService.listGroup());
    }

    @PutMapping()
    public R<?> update(@RequestBody GroupUpdateReq req) {
        iGroupService.updateGroup(req);
        return R.ok();
    }

    @DeleteMapping("{gid}")
    public R<Boolean> deleteByGid(@PathVariable("gid") String gid) {
        return R.ok(iGroupService.deleteByGid(gid));
    }

    @PostMapping("sort")
    public R<?> sort(@RequestBody List<GroupSortReq> req) {
        iGroupService.sort(req);
        return R.ok();
    }
}
