package com.cdtelecom.redis.concurrent.locks;

import com.cdtelecom.redis.RedisLockUtil;
import com.cdtelecom.redis.RedisLockUtilTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class RedisReentrantLockTest{

    public static RedisReentrantLock lock = new RedisReentrantLock(false,"keyForLock14");
    public static int i = 0;

    @Test
    public void testLock() throws Exception {
        IncrementThread3 test1 = new IncrementThread3("thread1");
        IncrementThread3 test2 = new IncrementThread3("thread2");

        test1.start();
        test2.start();
        test1.join();
        test2.join();
        System.out.println(i);
    }


    class IncrementThread3 extends Thread{

        public IncrementThread3(String name){
            super.setName(name);
        }
        public void run(){
            for (int j = 0; j < 10000; j++) {
                lock.lock();
                lock.lock();
                try {
                    System.out.println(this.getName() + " " + i);
                    i++;
                } finally {
                    lock.unlock();
                    lock.unlock();
                }
            }
        }
    }
}
