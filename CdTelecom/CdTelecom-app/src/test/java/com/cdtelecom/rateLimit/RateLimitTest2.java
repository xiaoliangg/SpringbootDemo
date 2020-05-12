package com.cdtelecom.rateLimit;

import com.cdtelecom.logic.ILogic;
import com.cdtelecom.logic.IRateLogic;
import com.cdtelecom.logic.impl.OpenDataFlowLogic;
import com.cdtelecom.mapper.AssetInfoMapper;
import com.cdtelecom.po.AssetInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class RateLimitTest2 {

    @Autowired
    private IRateLogic rateLimit1Logic;

    @Test
    public void testRateLimit() throws Exception {
        for(int i = 0;i<2;i++){
            rateLimit1Logic.operate(i+"");
        }
    }
}