package me.hajoo.eventlistener.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import me.hajoo.eventlistener.event.CustomSpringEvent;

@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {

	@Override
	public void onApplicationEvent(CustomSpringEvent event) {
		System.out.println("Received spring custom event - " + event.getMessage());
	}
}
