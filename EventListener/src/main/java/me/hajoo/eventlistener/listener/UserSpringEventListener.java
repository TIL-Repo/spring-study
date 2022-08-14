package me.hajoo.eventlistener.listener;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import me.hajoo.eventlistener.entity.User;
import me.hajoo.eventlistener.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserSpringEventListener {

	private final UserRepository userRepository;
	private final EntityManager entityManager;

	// @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	// @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	// @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void work(User user) {
		// BEFORE_COMMIT
		// entityManager.clear();

		// AFTER_COMMIT, AFTER_COMPLETION
		// if (userRepository.existsById(1L)) {
		// 	System.out.println("user that exists");
		// }

		System.out.println("Received spring user event");
	}
}
