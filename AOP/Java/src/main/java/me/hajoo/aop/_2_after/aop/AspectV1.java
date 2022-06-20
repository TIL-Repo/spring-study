package me.hajoo.aop._2_after.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV1 {

	@Around("execution(* me.hajoo.aop._2_after.order..*(..))")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[log] {}", joinPoint.getSignature());
		return joinPoint.proceed();
	}
}
