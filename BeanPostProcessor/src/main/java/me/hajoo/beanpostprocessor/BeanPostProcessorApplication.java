package me.hajoo.beanpostprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import me.hajoo.beanpostprocessor.config.AutoProxyConfig;

@SpringBootApplication
@Import(AutoProxyConfig.class)
public class BeanPostProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeanPostProcessorApplication.class, args);
	}

}
