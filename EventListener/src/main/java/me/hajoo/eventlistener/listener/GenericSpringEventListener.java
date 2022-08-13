package me.hajoo.eventlistener.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import me.hajoo.eventlistener.event.GenericSpringEvent;

@Component
public class GenericSpringEventListener {

	@EventListener(condition = "#event.success")
	public void work(GenericSpringEvent<String> event) {
		System.out.println("Received spring generic event - " + event.getWhat());
	}
}
