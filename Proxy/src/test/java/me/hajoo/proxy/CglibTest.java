package me.hajoo.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

import lombok.extern.slf4j.Slf4j;
import me.hajoo.proxy._2_cglib.ConcreteService;
import me.hajoo.proxy._2_cglib.TimeMethodInterceptor;

@Slf4j
public class CglibTest {

	@Test
	public void cglibTest() throws Exception {
	    // given
		final ConcreteService target = new ConcreteService();
		// when
		final Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ConcreteService.class);
		enhancer.setCallback(new TimeMethodInterceptor(target));
		final ConcreteService proxy = (ConcreteService) enhancer.create();
		// then
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass());
		proxy.call();
	}
}