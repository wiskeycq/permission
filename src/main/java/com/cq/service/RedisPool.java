package com.cq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/15 15:36
 * @Description:
 */
@Slf4j
@Service("redisPool")
public class RedisPool {

    @Resource(name="shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    //获取redis实例
    public ShardedJedis instance() {
        return shardedJedisPool.getResource();
    }

    public void safeClose(ShardedJedis shardedJedis) {
        try {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        } catch (Exception e) {
            log.error("return redis resource exception", e);
        }
    }
}
