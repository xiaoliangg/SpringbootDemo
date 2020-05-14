package com.cdtelecom.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//redis分布式锁
@Service(value="redisLock")
@Deprecated // 废弃，暂时不用
public class RedisLock {

    private static final int defaultExpire = 6000;
    @Autowired
    private RedisService redisService;
    public boolean lock(String key) {
        return lock(key, defaultExpire);
    }

    /**
     * 加锁 (不可重入、不可阻塞)
     * @param key redis key
     * @param expire 过期时间，单位毫秒
     * @return true:加锁成功，false，加锁失败
     */
    public boolean lock(String key, long expire) {
        return redisService.setnx(key,"1",expire);
    }

    public void unLock(String key) {
        redisService.remove(key);
    }
}
