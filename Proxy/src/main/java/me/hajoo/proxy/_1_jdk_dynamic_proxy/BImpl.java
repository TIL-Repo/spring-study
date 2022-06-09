package me.hajoo.proxy._1_jdk_dynamic_proxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BImpl implements BInterface {

	@Override
	public String call() {
		log.info("B 호출");
		return "B";
	}
}
