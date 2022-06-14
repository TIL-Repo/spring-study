package me.hajoo.proxyfactory.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.info("TimeAdvice 실행");
		long startTime = System.currentTimeMillis();

		Object result = invocation.proceed();

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("TimeAdvice 종료 resultTime = {}", resultTime);

		return result;
	}
}
