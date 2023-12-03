package com.arman.shortlink.admin.service;

import com.arman.shortlink.admin.dao.pojo.UserDo;
import com.arman.shortlink.admin.dto.req.UserLoginReq;
import com.arman.shortlink.admin.dto.req.UserRegisterReq;
import com.arman.shortlink.admin.dto.req.UserUpdateReq;
import com.arman.shortlink.admin.dto.resp.UserLoginResp;
import com.arman.shortlink.admin.dto.resp.UserResp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Arman
 */
public interface UserService extends IService<UserDo> {
    /**
     * 根据用户名查询 用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserResp getUserByUsername(String username);

    /**
     * 用户名是否已被注册
     *
     * @param username 用户名
     */
    Boolean hasUsername(String username);

    /**
     * 用户注册
     */
    void register(UserRegisterReq registerModel);

    /**
     * 修改用户信息
     */
    void updateUserInfo(UserUpdateReq updateReq);

    UserLoginResp login(UserLoginReq loginReq);

    /**
     * 检查用户是否登录
     */
    Boolean checkLogin(String username, String token);
}
