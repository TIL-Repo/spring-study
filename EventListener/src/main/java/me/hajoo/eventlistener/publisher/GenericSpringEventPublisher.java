package me.hajoo.eventlistener.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import me.hajoo.eventlistener.event.GenericSpringEvent;

@Component
public class GenericSpringEventPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void publicGenericStringEvent(String message, boolean success) {
		System.out.println("Publishing generic string event.");
		applicationEventPublisher.publishEvent(new GenericSpringEvent(message, success));
	}

	public void publicGenericIntegerEvent(Integer num, boolean success) {
		System.out.println("Publishing generic integer event.");
		applicationEventPublisher.publishEvent(new GenericSpringEvent(num, success));
	}
}
