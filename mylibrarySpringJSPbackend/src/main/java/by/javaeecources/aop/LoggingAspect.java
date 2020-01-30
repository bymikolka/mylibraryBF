package by.javaeecources.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Pointcut that matches all Spring beans in the application's main packages.
	 */
	@Pointcut("within(by.javaeecources.*)"  
			+ " || within(by.javaeecources.service..*)"
			+ " || within(by.javaeecourcess.controller..*)")
	public void applicationPackagePointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the advices.
	}

	/**
	 * Pointcut that matches all repositories, services and Web REST endpoints.
	 */
	@Pointcut("within(@org.springframework.stereotype.Repository *)"
			+ " || within(@org.springframework.stereotype.Service *)"
			+ " || within(@org.springframework.web.bind.annotation.RestController *)")
	public void springBeanPointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the advices.
	}

	@Before("springBeanPointcut()")
	public void logBeforeBean(JoinPoint joinPoint) {
		logger.info("logBefore Bean: {}", joinPoint.getSignature().getName());
	}

	@After("springBeanPointcut()")
	public void logAfterBean(JoinPoint joinPoint) {
		logger.info("logAfter Bean: {}", joinPoint.getSignature().getName());
	}
	
	
  @Around("applicationPackagePointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
      if (logger.isDebugEnabled()) {
          logger.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
              joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
      }
      try {
          Object result = joinPoint.proceed();
          if (logger.isDebugEnabled()) {
              logger.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                  joinPoint.getSignature().getName(), result);
          }
          return result;
      } catch (IllegalArgumentException e) {
          logger.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
              joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
          throw e;
      }
  }


}