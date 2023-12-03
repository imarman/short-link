package com.arman.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.arman.shortlink.admin.common.constant.RedisCacheKeyConst;
import com.arman.shortlink.admin.common.convention.BizException;
import com.arman.shortlink.admin.common.enums.RespEnum;
import com.arman.shortlink.admin.dao.mapper.UserMapper;
import com.arman.shortlink.admin.dao.pojo.UserDo;
import com.arman.shortlink.admin.dto.req.UserLoginReq;
import com.arman.shortlink.admin.dto.req.UserRegisterReq;
import com.arman.shortlink.admin.dto.req.UserUpdateReq;
import com.arman.shortlink.admin.dto.resp.UserLoginResp;
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
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    private final StringRedisTemplate stringRedisTemplate;

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
        RLock lock = redissonClient.getLock(RedisCacheKeyConst.LOCK_USER_REGISTER_KEY + registerModel.getUsername());

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

    @Override
    public void updateUserInfo(UserUpdateReq updateReq) {
        // TODO 2023/12/3: 验证当前用户是否为登录用户
        UserResp userByUsername = getUserByUsername(updateReq.getUsername());
        Optional.ofNullable(userByUsername).orElseThrow(() -> new BizException(RespEnum.CLIENT_ERROR, "用户不存在"));

        LambdaQueryWrapper<UserDo> wrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, userByUsername.getUsername());
        update(BeanUtil.toBean(updateReq, UserDo.class), wrapper);
    }

    @Override
    public UserLoginResp login(UserLoginReq loginReq) {
        LambdaQueryWrapper<UserDo> wrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, loginReq.getUsername())
                .eq(UserDo::getPassword, loginReq.getPassword());

        UserDo userDo = getOne(wrapper);
        Optional.ofNullable(userDo).orElseThrow(() -> new BizException(RespEnum.CLIENT_ERROR, "用户不存在"));

        Boolean hasKey = stringRedisTemplate.hasKey(RedisCacheKeyConst.USER_LOGIN_PREFIX + userDo.getUsername());
        if (Boolean.TRUE.equals(hasKey)) {
            throw new BizException(RespEnum.CLIENT_ERROR, "用户已登录");
        }

        // 放置重复登录，可以使用 hash 接口
        String token = UUID.randomUUID().toString(true);
        String hashKey = RedisCacheKeyConst.USER_LOGIN_PREFIX + userDo.getUsername();
        BoundHashOperations<String, Object, Object> operations = stringRedisTemplate.boundHashOps(hashKey);
        operations.put(token, JSON.toJSONString(userDo));
        operations.expire(30, TimeUnit.MINUTES);
        return new UserLoginResp(token);
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().hasKey(RedisCacheKeyConst.USER_LOGIN_PREFIX + username, token);
    }

}
