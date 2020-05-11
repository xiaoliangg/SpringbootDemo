package com.cdtelecom.aspect.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrintReqParam {
    /**
     * test
     * @return
     */
    double test() default Double.MAX_VALUE;

}
