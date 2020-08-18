package com.think.redis.service;

import com.think.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author think.wang
 * @version 1.0.0
 * @Title DistributedLockService
 * @Package com.think.redis.service
 * @Description 分布式锁实现
 * @date 2020/8/18
 */
@Slf4j
@Service
public class DistributedLockService {

    private static final Long RELEASE_SUCCESS = 1L;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey 锁
     * @param value   请求标识
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(String lockKey, String value) {
        return this.tryGetDistributedLock(lockKey, value, 3 * 1000);
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param value      请求标识
     * @param expireTime 过期时间
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(String lockKey, String value, long expireTime) {
        return redisUtils.setIfAbsent(lockKey, value, expireTime);
    }

    /**
     * 释放分布式锁
     * 相比Redis事务，Lua脚本的优点：
     * 减少网络开销：使用Lua脚本，无需向Redis 发送多次请求，执行一次即可，减少网络传输
     * 原子操作：Redis 将整个Lua脚本作为一个命令执行，原子，无需担心并发
     * 复用：Lua脚本一旦执行，会永久保存 Redis 中,，其他客户端可复用
     *
     * @param lockKey 锁
     * @param value   请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(String lockKey, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Object result = redisUtils.execute(redisScript, lockKey, value);
        return Objects.equals(RELEASE_SUCCESS, result);
    }

}
