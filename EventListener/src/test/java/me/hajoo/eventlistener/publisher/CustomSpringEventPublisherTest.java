package me.hajoo.eventlistener.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.hajoo.eventlistener.service.UserService;

@SpringBootTest
class CustomSpringEventPublisherTest {

	@Autowired
	private CustomSpringEventPublisher customSpringEventPublisher;
	@Autowired
	private GenericSpringEventPublisher genericSpringEventPublisher;

	@Autowired
	private UserService userService;

	@Test
	void generateEvent() {
		customSpringEventPublisher.publishCustomEvent("hello");
		genericSpringEventPublisher.publicGenericStringEvent("hello", true);
		genericSpringEventPublisher.publicGenericIntegerEvent(3, true);
	}

	@Test
	void userEventTest(){
		try {
			userService.signup();
		} catch (Exception e) {}
	}
}