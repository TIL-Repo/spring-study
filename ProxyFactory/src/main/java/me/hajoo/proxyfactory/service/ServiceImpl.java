package me.hajoo.proxyfactory.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceImpl implements Service {

	@Override
	public void hello() {
		log.info("Hello World!");
	}
}
