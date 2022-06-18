package me.hajoo.beanpostprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.hajoo.beanpostprocessor.service.HelloService;

@SpringBootTest
public class RegistBeanPostProcessorTest {

	@Autowired
	private HelloService helloService;

	@Test
	void test() throws Exception {
		helloService.hello();
		helloService.world();
	}
}
