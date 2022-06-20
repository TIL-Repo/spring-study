package me.hajoo.aop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import me.hajoo.aop._2_after.aop.AspectV1;
import me.hajoo.aop._2_after.aop.AspectV2;
import me.hajoo.aop._2_after.aop.AspectV3;
import me.hajoo.aop._2_after.order.OrderRepository;
import me.hajoo.aop._2_after.order.OrderService;

@Slf4j
@SpringBootTest
// @Import(AspectV1.class)
// @Import(AspectV2.class)
@Import({AspectV3.LogAspect.class, AspectV3.TransactionAspect.class})
public class AopTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	void aopInfo() throws Exception {
		log.info("isAopProxy, orderService = {}", AopUtils.isAopProxy(orderService));
		log.info("isAopProxy, orderRepository = {}", AopUtils.isAopProxy(orderRepository));
	}

	@Test
	void success() throws Exception {
		orderService.orderItem("itemA");
	}

	@Test
	void exception() throws Exception {
		Assertions.assertThrows(IllegalStateException.class, () -> orderService.orderItem("ex"));
	}
}
