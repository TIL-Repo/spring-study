package me.hajoo.aop._2_after.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV4 {

	@Around("me.hajoo.aop._2_after.aop.Pointcuts.orderAndAllService()")
	public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			// @Before
			log.info("[transaction begin] {}", joinPoint.getSignature());
			Object result = joinPoint.proceed( );
			// @AfterReturning
			log.info("[transaction end] {}", joinPoint.getSignature());
			return result;
		} catch (Exception e) {
			// @AfterThrowing
			log.info("[transaction rollback] {}", joinPoint.getSignature());
			throw e;
		} finally {
			// @After
			log.info("[release] {}", joinPoint.getSignature());
		}
	}

	@Before("me.hajoo.aop._2_after.aop.Pointcuts.orderAndAllService()")
	public void doBefore(JoinPoint joinPoint) {
		log.info("[before] {}", joinPoint.getSignature());
	}

	@AfterReturning(value = "me.hajoo.aop._2_after.aop.Pointcuts.orderAndAllService()", returning = "result")
	public void doReturn(JoinPoint joinPoint, Object result) {
		log.info("[afterReturning] {} return {}", joinPoint.getSignature(), result);
	}

	@AfterThrowing(value = "me.hajoo.aop._2_after.aop.Pointcuts.orderAndAllService()", throwing = "ex")
	public void doThrowing(JoinPoint joinPoint, Exception ex) {
		log.info("[afterThrowing] {} exception {}", joinPoint.getSignature(), ex.getMessage());
	}

	@After("me.hajoo.aop._2_after.aop.Pointcuts.orderAndAllService()")
	public void doAfter(JoinPoint joinPoint) {
		log.info("[after] {}", joinPoint.getSignature());
	}
}
