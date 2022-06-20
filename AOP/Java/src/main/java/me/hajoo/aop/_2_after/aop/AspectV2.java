package me.hajoo.aop._2_after.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV2 {

	@Around("me.hajoo.aop._2_after.aop.Pointcuts.allOrder()")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[log] {}", joinPoint.getSignature());
		return joinPoint.proceed();
	}

	@Around("me.hajoo.aop._2_after.aop.Pointcuts.orderAndAllService()")
	public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			log.info("[transaction begin] {}", joinPoint.getSignature());
			Object result = joinPoint.proceed();
			log.info("[transaction end] {}", joinPoint.getSignature());
			return result;
		} catch (Exception e) {
			log.info("[transaction rollback] {}", joinPoint.getSignature());
			throw e;
		} finally {
			log.info("[release] {}", joinPoint.getSignature());
		}
	}
}
