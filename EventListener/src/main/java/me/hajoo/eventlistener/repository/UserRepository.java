package me.hajoo.eventlistener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.hajoo.eventlistener.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
