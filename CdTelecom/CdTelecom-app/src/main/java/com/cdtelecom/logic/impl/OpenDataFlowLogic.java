package com.cdtelecom.logic.impl;

import com.cdtelecom.logic.ILogic;
import com.cdtelecom.pojo.response.BasicResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("openDataFlowLogic")
public class OpenDataFlowLogic  implements ILogic {

    @Override
    public BasicResponse operate(String requestStr, String triggerType) {
        return null;
    }
}
