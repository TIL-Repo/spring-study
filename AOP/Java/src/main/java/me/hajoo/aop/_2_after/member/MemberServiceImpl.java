package me.hajoo.aop._2_after.member;

import org.springframework.stereotype.Component;

import me.hajoo.aop._2_after.member.annotation.ClassAop;
import me.hajoo.aop._2_after.member.annotation.MethodAop;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

	@Override
	@MethodAop("test value")
	public String hello(String param) {
		return "hello";
	}

	public String world(String param) {
		return "world";
	}
}
