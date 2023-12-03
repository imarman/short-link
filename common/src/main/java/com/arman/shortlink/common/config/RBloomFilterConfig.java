package com.arman.shortlink.common.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 布隆过滤器配置
 *
 * @author Arman
 */
@Configuration
public class RBloomFilterConfig {

    /**
     * 防止用户注册查询数据库的布隆过滤器
     */
    @Bean
    public RBloomFilter<String> userRegisterCachePenetrationBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter("userRegisterCachePenetrationBloomFilter");
        // 参数一：预计布隆过滤器存储的元素的长度 (一亿个用户)
        // 参数二：期望的误判率 (0.001的误判率)
        cachePenetrationBloomFilter.tryInit(1_000_000, 0.001);
        return cachePenetrationBloomFilter;
    }

}
