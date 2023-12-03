package com.arman.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.arman.shortlink.admin.common.constant.RedissonLockConst;
import com.arman.shortlink.admin.common.convention.BizException;
import com.arman.shortlink.admin.common.enums.RespEnum;
import com.arman.shortlink.admin.dao.mapper.UserMapper;
import com.arman.shortlink.admin.dao.pojo.UserDo;
import com.arman.shortlink.admin.dto.req.UserRegisterReq;
import com.arman.shortlink.admin.dto.resp.UserResp;
import com.arman.shortlink.admin.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户接口实现
 *
 * @author Arman
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {

    private final RBloomFilter<String> bloomFilter;

    private final RedissonClient redissonClient;

    @Override
    public UserResp getUserByUsername(String username) {
        LambdaQueryWrapper<UserDo> wrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, username);
        UserDo userDo = getOne(wrapper);
        Optional.ofNullable(userDo).orElseThrow(() -> new BizException(RespEnum.CLIENT_ERROR, "用户不存在"));
        return BeanUtil.toBean(userDo, UserResp.class);
    }

    @Override
    public Boolean hasUsername(String username) {
        // 使用布隆过滤器判断是否已被注册，避免频繁调用DB
        return bloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReq registerModel) {
        // 判断用户名是否已经注册
        if (hasUsername(registerModel.getUsername())) {
            throw new BizException(RespEnum.USER_NAME_EXIST_ERROR);
        }

        // 通过分布式锁，防止用户并发注册
        RLock lock = redissonClient.getLock(RedissonLockConst.LOCK_USER_REGISTER_KEY + registerModel.getUsername());

        // 如果获取不到锁，说明已经有用户在注册了
        if (!lock.tryLock()) {
            throw new BizException(RespEnum.USER_NAME_EXIST_ERROR);
        }

        try {
            boolean saved = save(BeanUtil.toBean(registerModel, UserDo.class));
            if (!saved) {
                throw new BizException(RespEnum.USER_REGISTER_ERROR);
            }
            // 将用户名添加到布隆过滤器
            bloomFilter.add(registerModel.getUsername());
        } finally {
            lock.unlock();
        }
    }

}
