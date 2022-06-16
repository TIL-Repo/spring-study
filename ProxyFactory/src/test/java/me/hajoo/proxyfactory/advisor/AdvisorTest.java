package me.hajoo.proxyfactory.advisor;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import lombok.extern.slf4j.Slf4j;
import me.hajoo.proxyfactory.advice.TimeAdvice;
import me.hajoo.proxyfactory.service.Service;
import me.hajoo.proxyfactory.service.ServiceImpl;

@Slf4j
public class AdvisorTest {

	@Test
	@DisplayName("Advisor")
	void advisorTest() throws Exception {
		Service target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
		proxyFactory.addAdvisor(advisor);
		Service proxy = (Service)proxyFactory.getProxy();
		proxy.hello();
		proxy.world();
	}

	@Test
	@DisplayName("Pointcut")
	void PointcutTest() throws Exception {
		Service target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
		proxyFactory.addAdvisor(advisor);
		Service proxy = (Service)proxyFactory.getProxy();
		proxy.hello();
		proxy.world();
	}

	@Test
	@DisplayName("스프링에서 제공하는 Pointcut")
	void PointcutInSpring() throws Exception {
		Service target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("hello");
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
		proxyFactory.addAdvisor(advisor);
		Service proxy = (Service)proxyFactory.getProxy();
		proxy.hello();
		proxy.world();
	}

	static class MyPointcut implements Pointcut {

		@Override
		public ClassFilter getClassFilter() {
			return ClassFilter.TRUE;
		}

		@Override
		public MethodMatcher getMethodMatcher() {
			return new MyMethodMacher();
		}
	}

	static class MyMethodMacher implements MethodMatcher {

		final String methodName = "hello";


		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			boolean result = method.getName().equals(methodName);
			log.info("포인트컷 호출 method={} targetClass={}", method.getName(),targetClass);
			log.info("포인트컷 결과 result={}", result);
			return result;
		}

		@Override
		public boolean isRuntime() {
			return false;
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass, Object... args) {
			return false;
		}
	}
}
