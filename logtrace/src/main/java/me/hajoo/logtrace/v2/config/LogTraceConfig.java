package me.hajoo.logtrace.v2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.hajoo.logtrace.v2.FieldLogTraceImpl;
import me.hajoo.logtrace.v2.LogTrace;

@Configuration
public class LogTraceConfig {

	@Bean
	public LogTrace logTrace() {
		return new FieldLogTraceImpl();
	}
}
