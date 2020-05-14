package com.cdtelecom.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class RedisLockUtilTest {

    private static volatile int succesTimes = 0;
    private static final AtomicInteger failTimes = new AtomicInteger();
    private final static long expireTime = 30000;
    @Test
    public void testLock() throws Exception {
        String key = (int)(Math.random()*1000)+"";
        int loopTimes = 300;
        int threadNumber = 5;
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);

        for(int i=0;i<threadNumber;i++){
            new IncrementThread2(key,loopTimes,countDownLatch).start();
        }

        countDownLatch.await();
        System.out.println("succesTimes:" + succesTimes);
        System.out.println("failTimes:" + failTimes);
//        Thread.sleep(30*1000);
    }


    class IncrementThread2 extends Thread{
        private final String key;
        private int loopTimes;
        private CountDownLatch countDownLatch;
        public IncrementThread2(String key, int loopTimes, CountDownLatch countDownLatch){
            this.key = key;
            this.loopTimes = loopTimes;
            this.countDownLatch = countDownLatch;
        }
        public void run(){

            for(int i = 0;i<loopTimes;i++){
                boolean addSuccess = false;
                for(int j = 0;j<1000;i++){
                    if(RedisLockUtil.lock(key,expireTime)){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        succesTimes++;
                        addSuccess = true;
                        RedisLockUtil.unLock(key);
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


    @Test
    public void test() throws Exception {
        long time1 = System.currentTimeMillis();
        Thread.sleep(1);
        long time2 = System.currentTimeMillis();
        System.out.println("time:" + (time2-time1));
    }
}



