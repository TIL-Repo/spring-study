package me.hajoo.proxyfactory.advice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import lombok.extern.slf4j.Slf4j;
import me.hajoo.proxyfactory.service.ConcreteService;
import me.hajoo.proxyfactory.service.Service;
import me.hajoo.proxyfactory.service.ServiceImpl;

@Slf4j
class TimeAdviceTest {

	@Test
	@DisplayName("JDK 동적 프록시 사용")
	void jdk_dynamic_proxy() {
		Service target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());
		Service proxy = (Service) proxyFactory.getProxy();
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass());
		proxy.hello();

		Assertions.assertTrue(AopUtils.isAopProxy(proxy));
		Assertions.assertTrue(AopUtils.isJdkDynamicProxy(proxy));
		Assertions.assertFalse(AopUtils.isCglibProxy(proxy));
	}

	@Test
	@DisplayName("CGLIB 프록시 사용")
	void cglib_proxy() throws Exception {
		ConcreteService target = new ConcreteService();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());
		ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass());
		proxy.hello();

		Assertions.assertTrue(AopUtils.isAopProxy(proxy));
		Assertions.assertFalse(AopUtils.isJdkDynamicProxy(proxy));
		Assertions.assertTrue(AopUtils.isCglibProxy(proxy));
	}

	@Test
	@DisplayName("ProxyTargetOption 옵션을 사용하면 인터페이스가 있어도 CGLIB로 만들도록 할 수 있다.")
	void cglib_proxy_exists_interface() throws Exception {
		Service target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(true);
		proxyFactory.addAdvice(new TimeAdvice());
		Service proxy = (Service) proxyFactory.getProxy();
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass());
		proxy.hello();

		Assertions.assertTrue(AopUtils.isAopProxy(proxy));
		Assertions.assertFalse(AopUtils.isJdkDynamicProxy(proxy));
		Assertions.assertTrue(AopUtils.isCglibProxy(proxy));
	}
}