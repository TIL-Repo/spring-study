package me.hajoo.logtrace.v2.repository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hajoo.logtrace.common.trace.TraceStatus;
import me.hajoo.logtrace.v2.LogTrace;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

	private final LogTrace trace;

	public void save(String itemId) {
		TraceStatus status = null;
		try {
			status = trace.begin("OrderRepository.save()");

			if (itemId.equals("ex")) {
				throw new IllegalStateException("예외 발생");
			}
			sleep(1000);

			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}

	private void sleep(int ms) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
