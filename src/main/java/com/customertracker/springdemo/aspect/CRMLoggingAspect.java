package com.customertracker.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.customertracker.springdemo.controller.*.*(..))")
    private void forControllerPackages(){

    }

    @Pointcut("execution(* com.customertracker.springdemo.service.*.*(..))")
    private void forServicePackages(){

    }

    @Pointcut("execution(* com.customertracker.springdemo.dao.*.*(..))")
    private void forDAOPackages(){

    }

    @Pointcut("forControllerPackages() || forServicePackages() || forDAOPackages()")
    private void forAppFlow(){

    }

    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint){
        String method = joinPoint.getSignature().toShortString();
        logger.info("@Before called on: "+method);

        Object[] args = joinPoint.getArgs();
        for(Object tempArg : args) {
            logger.info("arguments list: " + tempArg);
        }
    }

    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "result"
    )
    public void afterReturning(JoinPoint joinPoint, Object result){
        String method = joinPoint.getSignature().toShortString();
        logger.info("@AfterReturning called on: "+method);

        logger.info("result: "+result);
    }
}
