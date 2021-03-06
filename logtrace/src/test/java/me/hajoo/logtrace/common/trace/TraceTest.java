package me.hajoo.logtrace.v1;

import org.junit.jupiter.api.Test;

import me.hajoo.logtrace.common.trace.TraceStatus;

class TraceTest {

	@Test
	void begin_end() throws Exception {
		Trace trace = new Trace();
		TraceStatus status = trace.begin("hello");
		trace.end(status);
	}

	@Test
	void begin_exception() throws Exception {
		Trace trace = new Trace();
		TraceStatus status = trace.begin("hello");
		trace.exception(status, new IllegalStateException());
	}

	@Test
	void recursive_begin_end() throws Exception {
		Trace trace = new Trace();
		TraceStatus status = trace.begin("hello");
		TraceStatus status2 = trace.beginSync(status.getTraceId(), "hello2");
		trace.end(status2);
		trace.end(status);
	}

	@Test
	void recursive_begin_exception() throws Exception {
		Trace trace = new Trace();
		TraceStatus status = trace.begin("hello");
		TraceStatus status2 = trace.beginSync(status.getTraceId(), "hello2");
		trace.exception(status2, new IllegalStateException());
		trace.exception(status, new IllegalStateException());
	}
}