package me.hajoo.proxy;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import me.hajoo.proxy._1_jdk_dynamic_proxy.AImpl;
import me.hajoo.proxy._1_jdk_dynamic_proxy.AInterface;
import me.hajoo.proxy._1_jdk_dynamic_proxy.BImpl;
import me.hajoo.proxy._1_jdk_dynamic_proxy.BInterface;
import me.hajoo.proxy._1_jdk_dynamic_proxy.TimeInvocationHandler;

@Slf4j
public class JdkDynamicProxyTest {
	
	@Test
	public void dynamicA() throws Exception {
		// given
		final AInterface target = new AImpl();
		final TimeInvocationHandler handler = new TimeInvocationHandler(target);
		// when
		final AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[] {AInterface.class},
			handler);
		// then
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass());
		proxy.call();
	}

	@Test
	public void dynamicB() throws Exception {
		// given
		final BInterface target = new BImpl();
		final TimeInvocationHandler handler = new TimeInvocationHandler(target);
		// when
		final BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[] {BInterface.class},
			handler);
		// then
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass());
		proxy.call();
	}
}
