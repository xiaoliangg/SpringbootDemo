package com.cdtelecom.logic.impl;

import com.cdtelecom.aspect.log.PrintReqParam;
import com.cdtelecom.aspect.ratelimit.RateLimit;
import com.cdtelecom.logic.ILogic;
import com.cdtelecom.pojo.response.QueryResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("rateLimit2Logic")
public class RateLimit2Logic implements ILogic {

    @Override
    @PrintReqParam(test = 1.2f)
    @RateLimit(perSecond = 0.1,timeOut = 3000)
    public QueryResponse operate(String requestStr, String triggerType) {
        QueryResponse response = new QueryResponse();
        response.setCommSeq("111");
        response.setErrorCode("0");
        return response;
    }
}
