package com.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
public class LogAspect {

    @Before("execution(* com.repository.CartRepo.*(..))")
    public void logBefore(JoinPoint joinPoint){
        System.out.println("Method " + joinPoint.getSignature().getName() + " Started At : " + LocalTime.now());
    }

    @After("execution(* com.repository.CartRepo.*(..))")
    public void logAfter(JoinPoint joinPoint){
        System.out.println("Method " + joinPoint.getSignature().getName() + " Ended At : " + LocalTime.now());
    }
}
