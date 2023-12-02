package com.arman.shortlink.admin.service.impl;

import com.arman.shortlink.admin.common.convention.BizException;
import com.arman.shortlink.admin.common.enums.RespEnum;
import com.arman.shortlink.admin.dao.mapper.UserMapper;
import com.arman.shortlink.admin.dao.pojo.UserDo;
import com.arman.shortlink.admin.dto.resp.UserResp;
import com.arman.shortlink.admin.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户接口实现
 *
 * @author Arman
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {

    @Override
    public UserResp getUserByUsername(String username) {
        LambdaQueryWrapper<UserDo> wrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, username);
        UserDo userDo = getOne(wrapper);
        Optional.ofNullable(userDo).orElseThrow(() -> new BizException(RespEnum.CLIENT_ERROR, "用户不存在"));
        UserResp target = new UserResp();
        BeanUtils.copyProperties(userDo, target);
        return target;
    }

}
