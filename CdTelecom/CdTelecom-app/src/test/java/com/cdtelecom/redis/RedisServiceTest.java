package com.cdtelecom.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cdtelecom.redis.RedisServiceTest.failInteger;
import static com.cdtelecom.redis.RedisServiceTest.successTimes;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class RedisServiceTest {
    @Autowired
    private RedisService redisService;

    /**
     * 测试set() get()
     * @throws Exception
     */
    @Test
    public void testSet_Get() throws Exception {
        String key = (int)(Math.random()*1000)+"";
        String value = (int)(Math.random()*1000)+"";

        boolean r = redisService.set(key,value);

        String valueR = (String)redisService.get(key);
        Assert.assertEquals(value,valueR);
    }
    /**
     * 测试批量读取
     * @throws Exception
     */
    @Test
    public void testMultiGet() throws Exception {
        String key1= (int)(Math.random()*1000)+"";
        String value1 = (int)(Math.random()*1000)+"";
        String key2= (int)(Math.random()*1000)+"";
        String value2 = (int)(Math.random()*1000)+"";


        redisService.set(key1,value1);
        redisService.set(key2,value2);
        List<Serializable> keyList = new ArrayList<>();
        keyList.add(key1);
        keyList.add(key2);
        List<Object> valueR = redisService.multiGet(keyList);
        System.out.println("结果:" + valueR.get(0) + "|原值:" + value1);
        System.out.println("结果:" + valueR.get(1) + "|原值:" + value2);
    }

    /**
     * 测试删除
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        String key= (int)(Math.random()*1000)+"";
        String value = (int)(Math.random()*1000)+"";
        redisService.set(key,value);

        String valueR = (String)redisService.get(key);
        System.out.println("结果:" + valueR+ "|原值:" + value);

        //多次remove，首次remove返回true，后续remove返回false
        boolean r;
        r = redisService.remove(key);
        System.out.println(r);
        r = redisService.remove(key);
        System.out.println(r);
        r = redisService.remove(key);
        System.out.println(r);
        valueR = (String)redisService.get(key);
        System.out.println("结果:" + valueR+ "|原值:" + value);
    }

    /**
     * 测试批量删除
     * @throws Exception
     */
    @Test
    public void testBatchRemove() throws Exception {
        String key= (int)(Math.random()*1000)+"";
        String value = (int)(Math.random()*1000)+"";
        String key2 = (int)(Math.random()*1000)+"";
        String value2 = (int)(Math.random()*1000)+"";

        redisService.set(key,value);
        redisService.set(key2,value2);

        String valueR = (String)redisService.get(key);
        String valueR2 = (String)redisService.get(key2);
        System.out.println("结果:" + valueR+ "|原值:" + value);
        System.out.println("结果:" + valueR2+ "|原值:" + value2);

        redisService.remove(key,key2);

        valueR = (String)redisService.get(key);
        valueR2 = (String)redisService.get(key2);
        System.out.println("结果:" + valueR+ "|原值:" + value);
        System.out.println("结果:" + valueR2+ "|原值:" + value2);
    }


    /**
     * 测试批量删除，使用pattern
     * @throws Exception
     */
    @Test
    public void testRemovePattern() throws Exception {
        String pattern = "patternTest_";

        String key= pattern + (int)(Math.random()*1000)+"";
        String value = (int)(Math.random()*1000)+"";
        String key2 = pattern + (int)(Math.random()*1000)+"";
        String value2 = (int)(Math.random()*1000)+"";

        redisService.set(key,value);
        redisService.set(key2,value2);

        String valueR = (String)redisService.get(key);
        String valueR2 = (String)redisService.get(key2);
        System.out.println("结果:" + valueR+ "|原值:" + value);
        System.out.println("结果:" + valueR2+ "|原值:" + value2);

        redisService.removePattern(pattern + "*");

        valueR = (String)redisService.get(key);
        valueR2 = (String)redisService.get(key2);
        System.out.println("结果:" + valueR+ "|原值:" + value);
        System.out.println("结果:" + valueR2+ "|原值:" + value2);
    }


    /**
     * 测试是否存在对应key
     * @throws Exception
     */
    @Test
    public void testExist() throws Exception {
        String key= (int)(Math.random()*1000)+"";
        String value = (int)(Math.random()*1000)+"";
        redisService.set(key,value);

        String valueR = (String)redisService.get(key);
        System.out.println("结果:" + valueR+ "|原值:" + value + "|exist:" + redisService.exists(key));

        redisService.remove(key);
        valueR = (String)redisService.get(key);
        System.out.println("结果:" + valueR+ "|原值:" + value + "|exist:" + redisService.exists(key));
    }

    /**
     * 测试set() 设置过期时间
     * @throws Exception
     */
    @Test
    public void testSetExpire() throws Exception {
        String key = (int)(Math.random()*1000)+"";
        String value = (int)(Math.random()*1000)+"";
        Long expireTime = 5*1000L;

        redisService.set(key,value,expireTime);

        Thread.sleep(expireTime-1000);
        String valueR = (String)redisService.get(key);
        System.out.println("sleep一段时间(小于过期时间)，查询结果:" + valueR + "|原值:" + value);

        Thread.sleep(1030);
        valueR = (String)redisService.get(key);
        System.out.println("sleep一段时间(大于过期时间)，查询结果:" + valueR + "|原值:" + value);
    }

    @Test
    public void testDecrement() throws Exception {
        String key = (int)(Math.random()*1000)+"";
        String value = 123 + "";
        redisService.set(key,value);

        long valueR = redisService.getDecrement(key);
        Assert.assertEquals(value,(valueR+1 + ""));
    }

    @Test
    public void testIncrement() throws Exception {
        String key = (int)(Math.random()*1000)+"";
        String value = 123 + "";
        redisService.set(key,value);

        long valueR = redisService.getIncrement(key);
        Assert.assertEquals(value,(valueR-1 + ""));
    }

    /**
     * 经测试，非原子操作
     * @throws Exception
     */
    @Test
    public void ConcurrentTestIncrement() throws Exception {
        String key = (int)(Math.random()*1000)+"";
        String value = 0 + "";

        int loopTimes = 30;
        int threadNumber = 30;
        for(int i=0;i<threadNumber;i++){
            new IncrementThread(redisService,key,loopTimes).start();
        }

        Long valueR = redisService.getIncrement(key);
        Assert.assertEquals(901 + "",valueR);
    }

    /*************************************************************************\
     * hash类型相关测试                                                       *
     \*************************************************************************/
    /**
     * 测试hset() hget()
     * @throws Exception
     */
    @Test
    public void testHset_Hget() throws Exception {
        String key = (int)(Math.random()*1000)+"";

        String hkey1 = (int)(Math.random()*1000)+"";
        String value1 = (int)(Math.random()*1000)+"";
        String hkey2 = (int)(Math.random()*1000)+"";
        String value2 = (int)(Math.random()*1000)+"";

        redisService.hSet(key,hkey1,value1);
        redisService.hSet(key,hkey2,value2);

        String valueR1 = (String)redisService.hGet(key,hkey1);
        String valueR2 = (String)redisService.hGet(key,hkey2);

        System.out.println("查询结果:" + valueR1 + "|原值:" + value1);
        System.out.println("查询结果:" + valueR2 + "|原值:" + value2);

    }

    /**
     * 批量获取hash类型
     * @throws Exception
     */
    @Test
    public void testMultiHget() throws Exception {
        String key = (int)(Math.random()*1000)+"";

        String hkey1 = (int)(Math.random()*1000)+"";
        String value1 = (int)(Math.random()*1000)+"";
        String hkey2 = (int)(Math.random()*1000)+"";
        String value2 = (int)(Math.random()*1000)+"";

        redisService.hSet(key,hkey1,value1);
        redisService.hSet(key,hkey2,value2);

        List<Object> keyList = new ArrayList<>();
        keyList.add(hkey1);
        keyList.add(hkey2);

        List<Object> valueR1 = redisService.multiHGet(key,keyList);

        System.out.println("查询结果:" + valueR1.get(0) + "|原值:" + value1);
        System.out.println("查询结果:" + valueR1.get(1) + "|原值:" + value2);

    }

    /**
     * 批量添加hash类型
     * @throws Exception
     */
    @Test
    public void testMultiHset() throws Exception {
        String key = (int)(Math.random()*1000)+"";

        String hkey1 = (int)(Math.random()*1000)+"";
        String value1 = (int)(Math.random()*1000)+"";
        String hkey2 = (int)(Math.random()*1000)+"";
        String value2 = (int)(Math.random()*1000)+"";

        Map<Object,Object> map = new HashMap<>();
        map.put(hkey1,value1);
        map.put(hkey2,value2);
        redisService.hMultiSet(key,map);

        List<Object> keyList = new ArrayList<>();
        keyList.add(hkey1);
        keyList.add(hkey2);

        List<Object> valueR1 = redisService.multiHGet(key,keyList);

        System.out.println("查询结果:" + valueR1.get(0) + "|原值:" + value1);
        System.out.println("查询结果:" + valueR1.get(1) + "|原值:" + value2);

    }


    /**
     * 批量删除hash类型
     * @throws Exception
     */
    @Test
    public void testMultiHRemove() throws Exception {
        String key = (int)(Math.random()*1000)+"";

        String hkey1 = (int)(Math.random()*1000)+"";
        String value1 = (int)(Math.random()*1000)+"";
        String hkey2 = (int)(Math.random()*1000)+"";
        String value2 = (int)(Math.random()*1000)+"";

        Map<Object,Object> map = new HashMap<>();
        map.put(hkey1,value1);
        map.put(hkey2,value2);
        redisService.hMultiSet(key,map);

        List<Object> keyList = new ArrayList<>();
        keyList.add(hkey1);
        keyList.add(hkey2);

        List<Object> valueR1 = redisService.multiHGet(key,keyList);
        System.out.println("查询结果:" + valueR1.get(0) + "|原值:" + value1);
        System.out.println("查询结果:" + valueR1.get(1) + "|原值:" + value2);

        //删除
        redisService.multiHRemove(key,hkey1,hkey2);

        valueR1 = redisService.multiHGet(key,keyList);
        System.out.println("删除后查询结果:" + valueR1.get(0) + "|原值:" + value1);
        System.out.println("删除后查询结果:" + valueR1.get(1) + "|原值:" + value2);
    }


    /*************************************************************************\
     * 并发相关操作测试                                                       *
     \*************************************************************************/
    /**
     * 测试setnx()
     * @throws Exception
     */
    @Test
    public void testSetnx() throws Exception {
        String key = (int)(Math.random()*1000)+"";
        String value = (int)(Math.random()*1000)+"";


        boolean r1 = redisService.setnx(key,value);
        boolean r2 = redisService.setnx(key,value);

        System.out.println("setnx()测试结果:首次setnx:" + r1 + "|再次setnx():" + r2 + "|key:" + key + "|value:" + value);

        Object o = redisService.get(key);
        System.out.println("get():" + o);
    }

    /**
     * 测试setnx()原子性
     */
    public static final AtomicInteger failInteger = new AtomicInteger();
    public static int successTimes;
    @Test
    public void testConcurrentSetnx() throws Exception {
        String key = (int)(Math.random()*1000)+"";
        int loopTimes = 100;
        int threadNumber = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);

        for(int i=0;i<threadNumber;i++){
            new SetnxThread(key,loopTimes,countDownLatch,redisService).start();
        }
        countDownLatch.await();
        System.out.println("succesTimes:" + successTimes);
        System.out.println("failInteger:" + failInteger);
    }


    /**
     * 测试 getSet(final String key, Object value)
     * @throws Exception
     */
    @Test
    public void testGetSet() throws Exception {
        String key = (int)(Math.random()*1000)+"";
        String value = (int)(Math.random()*1000)+"";

        boolean r1 = redisService.setnx(key,value);

        String value2 = (int)(Math.random()*1000)+"";
        String r = (String)redisService.getSet(key,value2);

        System.out.println("getSet()测试结果:getSet()获取值:" + r + "原值:" + value);
    }
}










class IncrementThread extends Thread{
    private RedisService redisService;
    private String key;
    private int loopTimes;
    public IncrementThread(RedisService redisService,String key,int loopTimes){
        this.redisService = redisService;
        this.key = key;
        this.loopTimes = loopTimes;
    }
    public void run(){
        for(int i = 0;i<loopTimes;i++){
            redisService.getIncrement(key);
        }

    }
}

class SetnxThread extends Thread{
    private String key;
    private int loopTimes;
    private CountDownLatch countDownLatch;
    private RedisService redisService;
//    private AtomicInteger failInteger;


    public SetnxThread(String key, int loopTimes, CountDownLatch countDownLatch, RedisService redisService) {
        this.key = key;
        this.loopTimes = loopTimes;
        this.countDownLatch = countDownLatch;
        this.redisService = redisService;
//        this.failInteger = failInteger;
    }

    public void run(){
        for(int i = 0;i<loopTimes;i++){
//            String value = (int)(Math.random()*1000)+"";
            String value = "1";
            setnxAndAdd(value);
        }
        countDownLatch.countDown();
    }

    private void setnxAndAdd(String value) {
        for(int i =0;i<100;i++){
            if(redisService.setnx(key,value,30000L)){
                successTimes = successTimes +1;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisService.remove(key);
                return;
            }
            try {
//                Thread.sleep((long)(Math.random()*1000));
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        failInteger.incrementAndGet();

    }
}
