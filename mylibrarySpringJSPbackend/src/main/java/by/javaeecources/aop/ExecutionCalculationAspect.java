package by.javaeecources.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Aspect
@Configuration
@Component
public class ExecutionCalculationAspect {


    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Around("@annotation(by.javaeecources.aop.LogExecutionTime)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    	long startTime = System.currentTimeMillis();
    	Object proceed = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken by {} is {}", joinPoint, timeTaken);
        return proceed;
    }
   
    
}