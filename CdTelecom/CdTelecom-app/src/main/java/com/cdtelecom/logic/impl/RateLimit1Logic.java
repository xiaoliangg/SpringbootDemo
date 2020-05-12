package com.cdtelecom.logic.impl;

import com.cdtelecom.aspect.log.PrintReqParam;
import com.cdtelecom.aspect.ratelimit.RateLimit;
import com.cdtelecom.logic.ILogic;
import com.cdtelecom.logic.IRateLogic;
import com.cdtelecom.pojo.response.QueryResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("rateLimit1Logic")
public class RateLimit1Logic implements IRateLogic {

    @Override
    @PrintReqParam(test = 1.2f)
    @RateLimit(perSecond = 0.1,timeOut = 3000)
    public String operate(String requestStr) {
        return requestStr;
    }
}
