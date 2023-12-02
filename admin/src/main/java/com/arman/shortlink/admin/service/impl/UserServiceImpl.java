package com.arman.shortlink.admin.service.impl;

import com.arman.shortlink.admin.dao.mapper.UserMapper;
import com.arman.shortlink.admin.dao.pojo.UserDo;
import com.arman.shortlink.admin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Arman
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {
}
