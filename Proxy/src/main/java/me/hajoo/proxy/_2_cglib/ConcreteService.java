package me.hajoo.proxy._2_cglib;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteService {

	public void call() {
		log.info("call 호출");
	}
}
