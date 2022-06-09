package me.hajoo.proxy._1_jdk_dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

	private final Object target;

	public TimeInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		log.info("TimeProxy 실행");
		final long startTime = System.currentTimeMillis();

		final Object result = method.invoke(target, args);

		final long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("TimeProxy 종료 resultTime = {}", resultTime);
		return result;
	}
}
