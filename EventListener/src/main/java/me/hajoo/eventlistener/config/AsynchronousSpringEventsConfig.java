package me.hajoo.eventlistener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

@Configuration
public class AsynchronousSpringEventsConfig {

	@Bean
	public ApplicationEventMulticaster applicationEventMulticaster() {
		SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
		// simpleApplicationEventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
		return simpleApplicationEventMulticaster;
	}
}
