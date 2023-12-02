package com.arman.shortlink.admin.service;

import com.arman.shortlink.admin.dao.pojo.UserDo;
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
}
