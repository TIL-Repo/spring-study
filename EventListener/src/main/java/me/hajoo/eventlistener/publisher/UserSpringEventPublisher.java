package me.hajoo.eventlistener.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import me.hajoo.eventlistener.entity.User;

@Component
public class UserSpringEventPublisher {

	@Autowired
	public ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public void publish(User user) {
		applicationEventPublisher.publishEvent(user);
	}
}
