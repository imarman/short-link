package com.arman.shortlink.admin.controller;

import com.arman.shortlink.admin.common.convention.R;
import com.arman.shortlink.admin.dto.req.resp.UserResp;
import com.arman.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理控制层
 *
 * @author Arman
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("{username}")
    public R<UserResp> getUserByUsername(@PathVariable("username") String username) {
        UserResp userByUsername = userService.getUserByUsername(username);
        return R.ok(userByUsername);
    }
}
