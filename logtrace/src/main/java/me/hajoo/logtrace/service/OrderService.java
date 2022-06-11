package me.hajoo.logtrace.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hajoo.logtrace.common.trace.Trace;
import me.hajoo.logtrace.common.trace.TraceId;
import me.hajoo.logtrace.common.trace.TraceStatus;
import me.hajoo.logtrace.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final Trace trace;

	public void orderItem(TraceId traceId, String itemId) {
		TraceStatus status = null;
		try {
			status = trace.beginSync(traceId, "OrderService.orderItem()");
			orderRepository.save(status.getTraceId(), itemId);
			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
}
