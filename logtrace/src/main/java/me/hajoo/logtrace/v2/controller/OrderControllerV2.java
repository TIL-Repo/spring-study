package me.hajoo.logtrace.v2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.hajoo.logtrace.common.trace.TraceStatus;
import me.hajoo.logtrace.v2.LogTrace;
import me.hajoo.logtrace.v2.service.OrderServiceV2;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

	private final OrderServiceV2 orderServiceV2;
	private final LogTrace trace;

	@GetMapping("v2/request")
	public String request(String itemId) {
		TraceStatus status = null;
		try {
			status = trace.begin("OrderController.request()");
			orderServiceV2.orderItem(itemId);
			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
		return "ok";
	}
}
