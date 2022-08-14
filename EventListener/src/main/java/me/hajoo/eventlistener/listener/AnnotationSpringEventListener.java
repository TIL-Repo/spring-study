package me.hajoo.eventlistener.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import me.hajoo.eventlistener.event.CustomSpringEvent;

@Component
public class AnnotationSpringEventListener {

	@EventListener
	public void work(CustomSpringEvent event) {
		System.out.println("Received spring annotation event - " + event.getMessage());
	}
}
