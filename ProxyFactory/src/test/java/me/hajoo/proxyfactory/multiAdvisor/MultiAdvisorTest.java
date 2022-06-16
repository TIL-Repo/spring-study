package me.hajoo.proxyfactory.multiAdvisor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import lombok.extern.slf4j.Slf4j;
import me.hajoo.proxyfactory.service.Service;
import me.hajoo.proxyfactory.service.ServiceImpl;

public class MultiAdvisorTest {

	@Test
	@DisplayName("여러 프록시")
	void multiAdvisorTest() throws Exception {
		Service target = new ServiceImpl();

		// proxy1
		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
		proxyFactory.addAdvisor(advisor);
		Service proxy = (Service)proxyFactory.getProxy();

		// proxy2
		ProxyFactory proxyFactory2 = new ProxyFactory(proxy);
		DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
		proxyFactory.addAdvisor(advisor2);
		Service proxy2 = (Service)proxyFactory.getProxy();

		proxy2.hello();
		proxy2.world();
	}

	@Test
	@DisplayName("하나 프록시, 여러 어드바이저")
	void oneProxyWithAdvisor() throws Exception {
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
		DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

		Service target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);

		proxyFactory.addAdvisor(advisor);
		proxyFactory.addAdvisor(advisor2);
		Service proxy = (Service)proxyFactory.getProxy();

		proxy.hello();
	}

	@Slf4j
	static class Advice1 implements MethodInterceptor {
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("advice1 호출");
			return invocation.proceed();
		}
	}

	@Slf4j
	static class Advice2 implements MethodInterceptor {
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("advice2 호출");
			return invocation.proceed();
		}
	}
}
