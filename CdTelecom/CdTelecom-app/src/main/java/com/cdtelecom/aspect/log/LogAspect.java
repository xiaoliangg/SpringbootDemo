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
        logger.info("拦截到了{}方法...", pjp.getSignature());
        logger.info("pjp.getArgs():{}" ,pjp.getArgs());
        logger.info("pjp.getKind():{}" ,pjp.getKind());
        logger.info("pjp.getSourceLocation():{}" ,pjp.getSourceLocation());
        logger.info("pjp.getStaticPart():{}" ,pjp.getStaticPart());
        logger.info("pjp.getTarget():{}" ,pjp.getTarget());
        logger.info("pjp.getThis():{}" ,pjp.getThis());
        logger.info("pjp.getClass():{}" ,pjp.getClass());
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        //获取目标方法
        Method targetMethod = methodSignature.getMethod();
        logger.info("目标方法所有注解:" + targetMethod.getDeclaredAnnotations());
        if (targetMethod.isAnnotationPresent(PrintReqParam.class)) {
            logger.info("包含自定义注解:" + PrintReqParam.class.getName());
        }
        logger.info(pjp.getArgs() + "开始执行!");
        Object result = pjp.proceed();
        logger.info(pjp.getArgs() + "执行完成:" + result);
        return result;
    }
}
