package me.hajoo.proxy._2_cglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

	private final Object target;

	public TimeMethodInterceptor(Object target) {
		this.target = target;
	}

	@Override
	public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		log.info("TimeProxy 실행");
		final long startTime = System.currentTimeMillis();

		final Object result = methodProxy.invoke(target, args);

		final long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("TimeProxy 종료 resultTime = {}", resultTime);
		return result;
	}
}
