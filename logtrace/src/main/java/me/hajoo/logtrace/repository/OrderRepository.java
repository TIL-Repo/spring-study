package me.hajoo.logtrace.repository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.hajoo.logtrace.common.trace.Trace;
import me.hajoo.logtrace.common.trace.TraceId;
import me.hajoo.logtrace.common.trace.TraceStatus;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final Trace trace;

	public void save(TraceId traceId, String itemId) {
		TraceStatus status = null;
		try {
			status = trace.beginSync(traceId, "OrderRepository.save()");

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
