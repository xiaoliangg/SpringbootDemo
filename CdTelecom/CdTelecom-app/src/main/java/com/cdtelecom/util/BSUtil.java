package com.cdtelecom.util;

import com.cdtelecom.Exception.BusinessException;

public class BSUtil {
    public static void isTrue(boolean expression, String error){
        if(!expression) {
            throw new BusinessException(error);
        }
    }
}
