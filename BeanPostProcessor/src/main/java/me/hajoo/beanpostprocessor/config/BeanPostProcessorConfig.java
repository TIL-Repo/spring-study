package me.hajoo.beanpostprocessor.config;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

// @Configuration
public class BeanPostProcessorConfig {

	@Bean
	public PostProcessor postProcessor() {
		return new PostProcessor(getAdvisor());
	}

	private Advisor getAdvisor() {
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("hello");
		return new DefaultPointcutAdvisor(pointcut, new TestAdvice());
	}

	@Slf4j
	static class TestAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			String methodName = invocation.getMethod().getName();
			log.info("create advice methodName = {}", methodName);
			return invocation.proceed();
		}
	}

	@Slf4j
	static class PostProcessor implements BeanPostProcessor {

		private final Advisor advisor;

		public PostProcessor(Advisor advisor) {
			this.advisor = advisor;
		}

		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
			log.info("beanName = {}, bean = {}", bean, beanName);

			String packageName = bean.getClass().getPackageName();
			if (packageName.startsWith("me.hajoo.beanpostprocessor.service")) {
				ProxyFactory proxyFactory = new ProxyFactory(bean);
				proxyFactory.addAdvisor(advisor);
				Object proxy = proxyFactory.getProxy();
				log.info("create proxy bean = {} proxy = {}", bean.getClass(), proxy.getClass());
				return proxy;
			}

			return bean;
		}
	}
}
