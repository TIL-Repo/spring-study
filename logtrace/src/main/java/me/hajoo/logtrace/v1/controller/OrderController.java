package me.hajoo.logtrace.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.hajoo.logtrace.common.trace.TraceStatus;
import me.hajoo.logtrace.v1.Trace;
import me.hajoo.logtrace.v1.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final Trace trace;

	@GetMapping("request")
	public String request(String itemId) {
		TraceStatus status = null;
		try {
			status = trace.begin("OrderController.request()");
			orderService.orderItem(status.getTraceId(), itemId);
			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
		return "ok";
	}
}
