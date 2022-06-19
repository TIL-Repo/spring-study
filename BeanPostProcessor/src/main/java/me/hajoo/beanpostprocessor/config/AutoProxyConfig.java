package me.hajoo.beanpostprocessor.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoProxyConfig {

	// @Bean
	public Advisor advisor() {
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("hello");
		return new DefaultPointcutAdvisor(pointcut, new BeanPostProcessorConfig.TestAdvice());
	}

	// @Bean
	public Advisor advisor2() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* me.hajoo.beanpostprocessor.service..*(..)) && !execution(* me.hajoo.beanpostprocessor.service..world(..))");
		return new DefaultPointcutAdvisor(pointcut, new BeanPostProcessorConfig.TestAdvice());
	}

	@Bean
	public AspectAdvisor advisor3() {
		return new AspectAdvisor();
	}
}
