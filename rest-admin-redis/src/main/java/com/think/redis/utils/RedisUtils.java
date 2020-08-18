package com.think.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time){
        try {
            if(time > 0){
               redisTemplate.expire(key, time, TimeUnit.SECONDS) ;
               return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }


    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     * @param key
     * @return
     */
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value){
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * setIfAbsent
     * @param key
     * @param value
     * @param timeOut
     * @return
     */
    public boolean setIfAbsent(String key, Object value, long timeOut){
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value, timeOut, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * setIfAbsent
     * @param key
     * @param value
     * @param timeOut
     * @param timeUnit
     * @return
     */
    public boolean setIfAbsent(String key, Object value, long timeOut, TimeUnit timeUnit){
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value, timeOut, timeUnit);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * lua脚本执行
     * @param redisScript
     * @param lockKey
     * @param value
     * @return
     */
    public Object execute(RedisScript<Long> redisScript, String lockKey, String value){
        return redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value);
    }

    /**
     * lua脚本执行
     * @param redisScript
     * @param lockKey
     * @param limitCount
     * @param limitPeriod
     * @return
     */
    public Object execute(RedisScript<Long> redisScript, String lockKey, int limitCount, int limitPeriod){
        return redisTemplate.execute(redisScript, Collections.singletonList(lockKey), limitCount, limitPeriod);
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                this.set(key, value);
            }
            return true;
        } catch (Exception e) {
           log.error(e.getMessage());
        }
        return false;
    }

    /**
     * 递增
     * @param key
     * @param no
     * @return
     */
    public long incr(String key, long no){
        if (no < 0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, no);
    }

}
