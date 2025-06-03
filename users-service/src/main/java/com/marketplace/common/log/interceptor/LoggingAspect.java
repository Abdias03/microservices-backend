package com.marketplace.common.log.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    
    @Pointcut("execution(* com.serviciosapp..controller.*.*(..))")
    public void controllerMethods() {}
    
    @Pointcut("execution(* com.serviciosapp..service.*.*(..))")
    public void serviceMethods() {}
    
    @Around("controllerMethods() || serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("Executing method: {}.{}() with args: {}", 
                   className, methodName, joinPoint.getArgs());
        
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            
            logger.info("Method {}.{}() executed successfully in {} ms", 
                       className, methodName, (endTime - startTime));
            
            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            
            logger.error("Method {}.{}() failed after {} ms with exception: {}", 
                        className, methodName, (endTime - startTime), e.getMessage(), e);
            
            throw e;
        }
    }
    
    @AfterThrowing(pointcut = "controllerMethods() || serviceMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.error("Exception in method {}.{}(): {}", 
                    className, methodName, exception.getMessage(), exception);
    }
}
