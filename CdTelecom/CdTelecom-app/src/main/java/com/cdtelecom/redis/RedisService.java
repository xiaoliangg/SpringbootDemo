package com.cdtelecom.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis的service
 * @author sxy
 *
 */
@Service(value="redisService")
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
    //	public static final String systemName = BicsConstant.SYSTEM_NAME;
//	public static final String eventCode = BicsConstant.EVENT_CODE;
//	public static final String content = BicsConstant.REDIS_CONTENT;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 递增 每次增1
     * 非原子操作
     * @param key
     * @return
     */
    public Long getIncrement(final String key) {
        Long result = null;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            //不加如下两行,会报错 ERR value is not an integer or out of range; nested exception is redis.clients.jedis.exceptions.JedisDataException
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            result = operations.increment(key, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 递减 每次减1
     * 非原子操作
     * @param key
     * @return
     */
    public Long getDecrement(final String key) {
        Long result = null;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            //不加如下两行,会报错 ERR value is not an integer or out of range; nested exception is redis.clients.jedis.exceptions.JedisDataException
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            result = operations.decrement(key, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存(支持过期)
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            operations.set(key, value);
            if(expireTime != 0){
                redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 批量读取
     * @param key
     * @return
     */
    public List<Object> multiGet(Collection<Serializable> key) {
        List<Object> result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.multiGet(key);
        return result;
    }
    /**
     * 删除
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 批量删除
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }
    /**
     * 批量删除,使用pattern
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

     /*************************************************************************\
     * 如下为哈希类型相关操作                                                  *
     \*************************************************************************/
    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希 批量添加
     * @param key
     * @param map
     */
    public void hMultiSet(String key, Map<Object,Object> map){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        hash.putAll(key,map);
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }
    /**
     * 哈希获取key对应的指定field-value数据
     * @param key
     * @param hashKeys
     * @return
     */
    public List<Object> multiHGet(String key, Collection<Object> hashKeys){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.multiGet(key,hashKeys);
    }
    /**
     * 哈希 删除key对应的指定field-value数据
     * @param key
     * @param hashKeys
     * @return
     */
    public void multiHRemove(String key, Object... hashKeys){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        hash.delete(key,hashKeys);
    }






    /*************************************************************************\
     * 如下为列表类型相关操作(TODO 暂未测试)                                   *
     \*************************************************************************/
    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,Object v){
        try {
            ListOperations<String, Object> list = redisTemplate.opsForList();
            list.rightPush(k,v);
        } catch (Exception e) {
            logger.error("向redis插入数据时连接redis失败",e);
        }
    }

    /**
     * 列表删除
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public void lTrim(String k, long l, long l1){
        try {
            ListOperations<String, Object> list = redisTemplate.opsForList();
            list.trim(k, l, l1);
        } catch (Exception e) {
            logger.error("向redis移除数据时连接redis失败",e);
//			SynSendMailUtil synSendMailUtil = new SynSendMailUtil(addCdrMapper,systemName,"向redis移除数据时连接redis失败",eventCode);
//			Thread t = new Thread(synSendMailUtil);
//			t.start();
        }
    }
    /**
     * 列表删除
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public void lBlop(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
    }
    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1){
        List<Object> range = null;
        try {
            ListOperations<String, Object> list = redisTemplate.opsForList();
            range = list.range(k,l,l1);
        } catch (Exception e) {
            logger.error("从redis取出数据时连接redis失败",e);
//			SynSendMailUtil synSendMailUtil = new SynSendMailUtil(addCdrMapper,systemName,"从redis取出数据时连接redis失败",eventCode);
//			Thread t = new Thread(synSendMailUtil);
//			t.start();
        }
        return range;
    }

    /**
     * 列表长度获取
     * @param k
     * @return
     */
    public long lSize(String k){
        long size= 0;
        try {

            ListOperations<String, Object> list = redisTemplate.opsForList();
            System.out.println(list);
            size = list.size(k);
        } catch (Exception e) {
            logger.error("从redis获取list长度时连接redis失败",e);
//			SynSendMailUtil synSendMailUtil = new SynSendMailUtil(addCdrMapper,systemName,"从redis获取list长度时连接redis失败",eventCode);
//			Thread t = new Thread(synSendMailUtil);
//			t.start();
        }
        return size;
    }


    /*************************************************************************\
     * 如下为集合类型相关操作(TODO 暂未测试)                                   *
     \*************************************************************************/
    /**
     * 集合添加(可过期)
     * @param key
     * @param value
     * @param expireTime
     */
    public void add(String key,Object value,Long expireTime){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
        if(expireTime != 0){
            redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<Object> getMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 集合刪除
     * @param key
     * @param value
     */
    public void removeFromSet(String key,Object... value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.remove(key,value);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }



    /**
     * 写入缓存,原子操作
     * setnx 的含义就是 SET if Not Exists，其主要有两个参数 setnx(key, value)。该方法是原子的，如果 key 不存在，则设置当前 key 成功，返回 true；如果当前 key 已经存在，则设置当前 key 失败，返回 false。
     * @param key
     * @param value
     * @return
     */
    public boolean setnx(final String key, Object value) {
        boolean result = false;
        try {
            BoundValueOperations<Serializable, Object> operations = redisTemplate.boundValueOps(key);
            result = operations.setIfAbsent(value);
//            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("异常:" + e.getMessage());
        }
        return result;
    }


    /**
     * 读取缓存
     * 这个命令主要有两个参数 getset(key，newValue)。该方法是原子的，对 key 设置 newValue 这个值，并且返回 key 原来的旧值。假设 key 原来是不存在的，那么多次执行这个命令，会出现下边的效果：
     * @param key
     * @return
     */
    public Object getSet(final String key, Object value) {
        Object oldValue = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        oldValue = operations.getAndSet(key,value);
        return oldValue;
    }
}
