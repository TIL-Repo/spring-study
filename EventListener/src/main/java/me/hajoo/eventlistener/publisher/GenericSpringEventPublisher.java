package me.hajoo.eventlistener.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import me.hajoo.eventlistener.event.GenericSpringEvent;

@Component
public class GenericSpringEventPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void publicGenericEvent(String message, boolean success) {
		System.out.println("Publishing generic event.");
		applicationEventPublisher.publishEvent(new GenericSpringEvent<>(message, success));
	}
}
