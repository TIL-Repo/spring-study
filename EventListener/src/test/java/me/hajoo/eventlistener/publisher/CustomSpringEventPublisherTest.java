package me.hajoo.eventlistener.publisher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomSpringEventPublisherTest {

	@Autowired
	private CustomSpringEventPublisher customSpringEventPublisher;
	@Autowired
	private GenericSpringEventPublisher genericSpringEventPublisher;

	@Test
	void generateEvent() throws Exception {
		customSpringEventPublisher.publishCustomEvent("hello");
		genericSpringEventPublisher.publicGenericEvent("hello", true);
	}
}