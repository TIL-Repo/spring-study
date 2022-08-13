package me.hajoo.eventlistener.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import me.hajoo.eventlistener.event.CustomSpringEvent;

@Component
public class CustomSpringEventPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void publishCustomEvent(String message) {
		System.out.println("Publishing custom event.");
		applicationEventPublisher.publishEvent(new CustomSpringEvent(this, message));
	}
}
