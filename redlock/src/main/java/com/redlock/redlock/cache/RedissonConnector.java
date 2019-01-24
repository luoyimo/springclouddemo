package com.redlock.redlock.cache;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 14:43 2018/5/17 0017
 */
@Component
public class RedissonConnector {

    RedissonClient redisson;

    @PostConstruct
    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("119.29.20.128:6379").setPassword("eees@#$!1234");
        redisson = Redisson.create(config);
    }

    public RedissonClient getClient() {
        return redisson;
    }
}
