package me.hajoo.beanpostprocessor.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HelloService {

	public void hello() { log.info("hello"); }

	public void world() { log.info("world"); }
}
