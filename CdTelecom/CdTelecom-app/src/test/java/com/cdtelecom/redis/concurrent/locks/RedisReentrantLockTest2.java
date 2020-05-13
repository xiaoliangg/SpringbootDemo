package com.cdtelecom.redis.concurrent.locks;

import com.cdtelecom.util.HttpClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 请求两个节点服务进行测试
 */
public class RedisReentrantLockTest2 {

    public static final String  url = "http://localhost:15023/addFileNumber";
    public static final String  url2 = "http://localhost:15022/addFileNumber";

    public static final String  jsonStr = "111";
    public static final String  charset = "utf-8";


    @Test
    public void testReidsLock() throws Exception {
        IncrementThread3 test1 = new IncrementThread3("thread1");
        IncrementThread3 test2 = new IncrementThread3("thread2");

        test1.start();
        test2.start();
        test1.join();
        test2.join();
    }


    class IncrementThread3 extends Thread{

        public IncrementThread3(String name){
            super.setName(name);
        }
        public void run(){
            for (int j = 0; j < 1000; j++) {
                if(j%2 == 0){
                    HttpClientUtil.doPost(url,jsonStr,charset);
                }else{
                    HttpClientUtil.doPost(url2,jsonStr,charset);
                }
            }
        }
    }
}
