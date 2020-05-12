package com.cdtelecom.rateLimit;

import com.cdtelecom.logic.ILogic;
import com.cdtelecom.logic.IRateLogic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class RateLimitTest3 {

    @Autowired
    private IRateLogic rateLimit1Logic;
    @Autowired
    private IRateLogic rateLimit2Logic;

    //两个方法共享一个RateLimiter测试
    @Test
    public void testRateLimit() throws Exception {
        rateLimit1Logic.operate("1");
        rateLimit2Logic.operate("2");
    }
}