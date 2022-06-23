package me.hajoo.aop;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import lombok.extern.slf4j.Slf4j;
import me.hajoo.aop._2_after.member.MemberServiceImpl;

@Slf4j
class ExecutionTest {

	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method helloMethod;

	@BeforeEach
	public void init() throws Exception {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	void printMethod() throws Exception {
		log.info("helloMethod = {}", helloMethod);
	}

	@Test
	void exactMatch() throws Exception {
		// public java.lang.String me.hajoo.aop._2_after.member.MemberServiceImpl.hello(java.lang.String)
		pointcut.setExpression("execution(public String me.hajoo.aop._2_after.member.MemberServiceImpl.hello(String))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void allMatch() throws Exception {
		pointcut.setExpression("execution(* *(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatch() throws Exception {
		pointcut.setExpression("execution(* hello(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatchStar1() throws Exception {
		pointcut.setExpression("execution(* hel*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatchStar2() throws Exception {
		pointcut.setExpression("execution(* *ello(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatchStar3() throws Exception {
		pointcut.setExpression("execution(* *ll*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatchStar4() throws Exception {
		pointcut.setExpression("execution(* hel*o(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageExactMatch1() throws Exception {
		pointcut.setExpression("execution(* me.hajoo.aop._2_after.member.MemberServiceImpl.hello(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageExactMatch2() throws Exception {
		pointcut.setExpression("execution(* me.hajoo.aop._2_after.member.*.hello(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageExactMatch3() throws Exception {
		pointcut.setExpression("execution(* me.hajoo.aop._2_after.member.*.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageExactMatch4() throws Exception {
		pointcut.setExpression("execution(* me.hajoo.aop._2_after.member.*.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageExactFalse() throws Exception {
		pointcut.setExpression("execution(* me.hajoo.aop._2_after.*.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	void packageMatchSubPackage1() throws Exception {
		pointcut.setExpression("execution(* me.hajoo.aop._2_after..*.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void typeExactMatch() throws Exception {
		pointcut.setExpression("execution(* me.hajoo.aop._2_after.member.MemberServiceImpl.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void typeMatchSuperType() throws Exception {
		pointcut.setExpression("execution(* me.hajoo.aop._2_after.member.MemberService.*(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void typeMatchWorld() throws Exception {
		pointcut.setExpression("execution(* me.hajoo.aop._2_after.member.MemberServiceImpl.*(..))");
		Method worldMethod = MemberServiceImpl.class.getMethod("world", String.class);
		Assertions.assertThat(pointcut.matches(worldMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void typeMatchNoSuperTypeMethodFalse() throws Exception {
		pointcut.setExpression("execution(* me.hajoo.aop._2_after.member.MemberService.*(..))");
		Method worldMethod = MemberServiceImpl.class.getMethod("world", String.class);
		Assertions.assertThat(pointcut.matches(worldMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	void argsMatch() throws Exception {
		pointcut.setExpression("execution(* *(String))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void argsMatchNoArgs() throws Exception {
		pointcut.setExpression("execution(* *())");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	void argsMatchStar() throws Exception {
		pointcut.setExpression("execution(* *(*))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void argsMatchAll() throws Exception {
		pointcut.setExpression("execution(* *(..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void argsMatchComplex() throws Exception {
		pointcut.setExpression("execution(* *(String, ..))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void argsMatchComplex2() throws Exception {
		pointcut.setExpression("execution(* *(String, *))");
		Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}
}