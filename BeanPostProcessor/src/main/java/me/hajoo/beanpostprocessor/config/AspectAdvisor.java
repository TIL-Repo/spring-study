package me.hajoo.beanpostprocessor.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectAdvisor {

	@Around("execution(* me.hajoo.beanpostprocessor.service..*(..))")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		String message = joinPoint.getSignature().toShortString();
		log.info("create advice methodName = {}", message);
		return joinPoint.proceed();
	}
}
