package me.hajoo.logtrace.v2.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hajoo.logtrace.common.trace.TraceStatus;
import me.hajoo.logtrace.v2.LogTrace;
import me.hajoo.logtrace.v2.repository.OrderRepositoryV2;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

	private final OrderRepositoryV2 orderRepositoryV2;
	private final LogTrace trace;

	public void orderItem(String itemId) {
		TraceStatus status = null;
		try {
			status = trace.begin("OrderService.orderItem()");
			orderRepositoryV2.save(itemId);
			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
}
