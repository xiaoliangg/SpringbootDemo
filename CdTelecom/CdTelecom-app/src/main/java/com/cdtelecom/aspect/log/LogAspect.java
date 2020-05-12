package com.cdtelecom.aspect.log;

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

@Component
@Aspect
public class LogAspect {

    public static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义切点
     * 1、通过扫包切入
     * 2、带有指定注解切入
     */
//    @Pointcut("execution(public * com.ycn.springcloud.*.*(..))")
    @Pointcut("@annotation(com.cdtelecom.aspect.log.PrintReqParam)")
    public void checkPointcut() { }

    @ResponseBody
    @Around(value = "checkPointcut()")
    public Object aroundNotice(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("打印log{}方法...", pjp.getSignature());
        logger.info("打印log pjp.getArgs():{}" ,pjp.getArgs()[0]);
        logger.info("打印log pjp.getKind():{}" ,pjp.getKind());
        logger.info("打印log pjp.getSourceLocation():{}" ,pjp.getSourceLocation());
        logger.info("打印log pjp.getStaticPart():{}" ,pjp.getStaticPart());
        logger.info("打印log pjp.getTarget():{}" ,pjp.getTarget());
        logger.info("打印log pjp.getThis():{}" ,pjp.getThis());
        logger.info("打印log pjp.getClass():{}" ,pjp.getClass());
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        //获取目标方法
        Method targetMethod = methodSignature.getMethod();
        logger.info("打印log  目标方法所有注解:" + targetMethod.getDeclaredAnnotations());
        if (targetMethod.isAnnotationPresent(PrintReqParam.class)) {
            logger.info("打印log  包含自定义注解:" + PrintReqParam.class.getName());
        }
        logger.info("打印log" + pjp.getArgs() + "开始执行! ");
        Object result = pjp.proceed();
        logger.info("打印log" + pjp.getArgs() + "执行完成:" + result);
        return result;
    }
}
