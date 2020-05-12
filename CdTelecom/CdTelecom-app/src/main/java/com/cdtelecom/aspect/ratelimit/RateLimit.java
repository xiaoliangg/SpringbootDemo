package com.cdtelecom.aspect.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     *
     * @return
     */
    String value() default "";

    /**
     * 每秒向桶中放入令牌的数量   默认最大即不做限流
     * @return
     */
    double perSecond() default Double.MAX_VALUE;
//    String perSecond() default "";

    /**
     * 获取令牌的等待时间  默认0
     * @return
     */
    int timeOut() default 0;
//    String timeOut() default "";

    /**
     * 超时时间单位
     * @return
     */
    TimeUnit timeOutUnit() default TimeUnit.MILLISECONDS;

}
