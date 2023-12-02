package com.arman.shortlink.admin.controller;

import com.arman.shortlink.admin.common.convention.R;
import com.arman.shortlink.admin.dto.req.UserRegisterReq;
import com.arman.shortlink.admin.dto.resp.UserResp;
import com.arman.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("exist/{username}")
    public R<Boolean> userExistByName(@PathVariable("username") String username) {
        return R.ok(userService.hasUsername(username));
    }

    @PostMapping
    public R<?> register(@RequestBody @Validated UserRegisterReq registerReq) {
        userService.register(registerReq);
        return R.ok();
    }
}
