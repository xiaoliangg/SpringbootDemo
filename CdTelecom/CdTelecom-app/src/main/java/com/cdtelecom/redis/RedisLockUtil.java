package com.cdtelecom.redis;


import com.cdtelecom.util.SpringUtil;

//redis分布式锁
public final class RedisLockUtil {
    //注意:过小会出问题，如当为60时，并发修改文件会出问题
    //分析:此属性含义为过期时间，当锁已过期，但是操作未完成时，就可能出现多个线程同时执行并发操作
    private static final int defaultExpire = 30*1000;

    private RedisLockUtil() {
        //
    }

//    /**
//     * 加锁
//     * @param key redis key
//     * @param expire 过期时间，单位秒
//     * @return true:加锁成功，false，加锁失败
//     */
//    public static boolean lock2(String key, int expire) {
//
//        RedisService redisService = (RedisService)SpringUtil.getBean("redisService");
//        long status = redisService.setnx(key, "1");
//
//        if(status == 1) {
//            redisService.expire(key, expire);
//            return true;
//        }
//
//        return false;
//    }

    public static boolean lock(String key) {
        return lock(key, defaultExpire);
    }

    /**
     * 加锁 (不可重入、不可阻塞)
     * 注意: 过期判断是以毫秒为粒度的，如果操作时间<1ms 可能会有问题
     * @param key redis key
     * @param expire 过期时间，单位毫秒
     * @return true:加锁成功，false，加锁失败
     */
    public static boolean lock(String key, long expire) {

        RedisService redisService = (RedisService)SpringUtil.getBean("redisService");


        long value = System.currentTimeMillis() + expire;
        boolean result = redisService.setnx(key, String.valueOf(value));

        if(result) {
            return true;
        }
        Object o = redisService.get(key);

       //o==null表示持有线程已释放锁，再次尝试获取锁。如果再获取不到锁，且获取不到当前值，就返回false
        if(o == null){
            result = redisService.setnx(key, String.valueOf(value));
        }
        if(result) {
            return true;
        }
        o = redisService.get(key);
        if(o == null){
            return false;
        }

        long oldExpireTime = Long.parseLong((String)o);
        if(oldExpireTime < System.currentTimeMillis()) {
            //超时
            long newExpireTime = System.currentTimeMillis() + expire;
            Object o2 = redisService.getSet(key, String.valueOf(newExpireTime));
            if(o2 == null){
                return true;
            }
            long currentExpireTime = Long.parseLong((String)o2);
            if(currentExpireTime == oldExpireTime) {
                return true;
            }
        }
        return false;
    }

//    public static void unLock2(String key) {
//        RedisService redisService = (RedisService)SpringUtil.getBean("redisService");
//        redisService.remove(key);
//    }

    public static void unLock(String key) {
        RedisService redisService = (RedisService) SpringUtil.getBean("redisService");
//        long oldExpireTime = Long.parseLong((String)redisService.get(key));
//        if(oldExpireTime > System.currentTimeMillis()) {
//            redisService.remove(key);
//        }
        redisService.remove(key);

    }
}
