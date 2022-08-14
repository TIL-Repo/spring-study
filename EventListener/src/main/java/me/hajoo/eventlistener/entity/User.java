package me.hajoo.eventlistener.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	@Id @GeneratedValue
	private Long id;

	public User() {

	}

	public User(Long id) {
		this.id = id;
	}
}
