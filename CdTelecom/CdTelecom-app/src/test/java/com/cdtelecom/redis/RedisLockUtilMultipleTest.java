package com.cdtelecom.redis;

import com.cdtelecom.util.HttpClientUtil;
import org.junit.Test;

/**
 * 测试多机时的redis锁。测试方法: 启用多个线程，每个线程都向多个url发送请求，每个请求的效果是向某个文件中的数+1.
 */
public class RedisLockUtilMultipleTest {

    public static final String  url = "http://localhost:15022/redisLockUtilMultiple";
    public static final String  url2 = "http://localhost:15023/redisLockUtilMultiple";

    public static final String  jsonStr = "111";
    public static final String  charset = "utf-8";


    @Test
    public void testReidsLock() throws Exception {
        IncrementThread3 test1 = new IncrementThread3("thread1");
        IncrementThread3 test2 = new IncrementThread3("thread2");
        IncrementThread3 test3 = new IncrementThread3("thread3");

        test1.start();
        test2.start();
        test3.start();
        test1.join();
        test2.join();
        test3.join();
    }


    class IncrementThread3 extends Thread{

        public IncrementThread3(String name){
            super.setName(name);
        }
        public void run(){
            for (int j = 0; j < 2000; j++) {
                if(j%2 == 0){
                    HttpClientUtil.doPost(url,jsonStr,charset);
                }else{
                    HttpClientUtil.doPost(url2,jsonStr,charset);
                }
            }
        }
    }
}



