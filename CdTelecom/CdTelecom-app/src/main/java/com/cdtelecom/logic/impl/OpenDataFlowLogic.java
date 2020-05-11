package com.cdtelecom.logic.impl;

import com.cdtelecom.aspect.log.PrintReqParam;
import com.cdtelecom.logic.ILogic;
import com.cdtelecom.pojo.response.BasicResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("openDataFlowLogic")
public class OpenDataFlowLogic  implements ILogic {

    @Override
    @PrintReqParam(test = 1.2f)
    public BasicResponse operate(String requestStr, String triggerType) {

        BasicResponse response = new BasicResponse();
        response.setCommSeq("111");
        response.setErrorCode("0");
        return response;
    }
}
