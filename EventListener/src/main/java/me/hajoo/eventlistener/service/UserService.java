package me.hajoo.eventlistener.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.hajoo.eventlistener.entity.User;
import me.hajoo.eventlistener.publisher.UserSpringEventPublisher;
import me.hajoo.eventlistener.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserSpringEventPublisher userSpringEventPublisher;

	@Transactional
	public void signup() throws Exception {
		userRepository.save(new User());
		userSpringEventPublisher.publish(new User());
		// AFTER_ROLLBACK
		// throw new RuntimeException();
	}
}
