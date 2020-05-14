package com.cdtelecom.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Deprecated // 废弃，暂时不用
public class RedisLockUtil2Test {

    public static int succesTimes;
    private static final AtomicInteger failTimes = new AtomicInteger();
    private final static long expireTime = 30000;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private RedisService redisService;

    @Test
    public void testLock() throws Exception {
        String key = (int)(Math.random()*1000)+"";
        int loopTimes = 300;
        int threadNumber = 5;
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);

        for(int i=0;i<threadNumber;i++){
            new IncrementThread2(key,loopTimes,countDownLatch,redisLock,redisService,failTimes,expireTime).start();
        }

        countDownLatch.await();
        System.out.println("succesTimes:" + succesTimes);
        System.out.println("failTimes:" + failTimes);
//        Thread.sleep(30*1000);
    }




    @Test
    public void test() throws Exception {
        long time1 = System.currentTimeMillis();
        Thread.sleep(1);
        long time2 = System.currentTimeMillis();
        System.out.println("time:" + (time2-time1));
    }
}

@Deprecated // 废弃，暂时不用
class IncrementThread2 extends Thread{
    private final String key;
    private int loopTimes;
    private CountDownLatch countDownLatch;
    private RedisLock redisLock;
    private RedisService redisService;
    private final AtomicInteger failTimes;
    private long expireTime = 30000;
    public IncrementThread2(String key, int loopTimes, CountDownLatch countDownLatch, RedisLock redisLock, RedisService redisService, AtomicInteger failTimes, long expireTime){
        this.key = key;
        this.loopTimes = loopTimes;
        this.countDownLatch = countDownLatch;
        this.redisLock = redisLock;
        this.redisService = redisService;
        this.failTimes = failTimes;
        this.expireTime = expireTime;
    }
    public void run(){

        for(int i = 0;i<loopTimes;i++){
            boolean addSuccess = false;
            for(int j = 0;j<1000;j++){
                if(redisLock.lock(key)){
//                        succesTimes++;
                    RedisLockUtil2Test.succesTimes = RedisLockUtil2Test.succesTimes + 1;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    addSuccess = true;
                    redisLock.unLock(key);
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(!addSuccess){
                failTimes.incrementAndGet();
            }
        }
        countDownLatch.countDown();
    }

}



