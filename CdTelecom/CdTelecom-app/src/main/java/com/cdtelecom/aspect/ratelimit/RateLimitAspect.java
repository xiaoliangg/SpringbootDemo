package com.cdtelecom.aspect.ratelimit;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;

/**
 * 使用aop和自定义注解方式实现限流。同样的注解共享一个令牌桶，即公用一个rateLimiter。 测试方法:设置每秒产生0.1令牌。然后依次调用已使用aop配置限流的两个service，则第二个会因为限流导致失败
 */
@Aspect
@Component
public class RateLimitAspect {

    public static Logger logger = LoggerFactory.getLogger(RateLimitAspect.class);

    private RateLimiter rateLimiter = RateLimiter.create(Double.MAX_VALUE);

    /**
     * 定义切点
     * 1、通过扫包切入
     * 2、带有指定注解切入
     */
//    @Pointcut("execution(public * com.ycn.springcloud.*.*(..))")
    @Pointcut("@annotation(RateLimit)")
    public void checkPointcut() { }

    @ResponseBody
    @Around(value = "checkPointcut()")
    public Object aroundNotice(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("拦截到了{}方法...", pjp.getSignature() + "requestId:" + pjp.getArgs()[0]);
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        //获取目标方法
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(RateLimit.class)) {
            //获取目标方法的@RateLimit注解
            RateLimit rateLimit = targetMethod.getAnnotation(RateLimit.class);
            rateLimiter.setRate(rateLimit.perSecond());
            if (!rateLimiter.tryAcquire(rateLimit.timeOut(), rateLimit.timeOutUnit())){
                logger.info("服务器繁忙,当前rateLimiter.getRate()" + rateLimiter.getRate());
                return "服务器繁忙，请稍后再试!";

            }
        }
        return pjp.proceed();
    }
}