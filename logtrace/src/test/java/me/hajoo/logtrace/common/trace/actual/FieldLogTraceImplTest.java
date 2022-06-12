package me.hajoo.logtrace.common.trace.actual;

import org.junit.jupiter.api.Test;

import me.hajoo.logtrace.common.trace.TraceStatus;
import me.hajoo.logtrace.v2.FieldLogTraceImpl;

class FieldLogTraceImplTest {

	FieldLogTraceImpl fieldLogTrace = new FieldLogTraceImpl();

	@Test
	void begin_end() throws Exception {
		TraceStatus status = fieldLogTrace.begin("trace1");
		TraceStatus status2 = fieldLogTrace.begin("trace2");
		fieldLogTrace.end(status2);
		fieldLogTrace.end(status);
	}

	@Test
	void begin_exception() throws Exception {
		TraceStatus status = fieldLogTrace.begin("trace1");
		TraceStatus status2 = fieldLogTrace.begin("trace2");
		fieldLogTrace.exception(status2, new IllegalStateException());
		fieldLogTrace.exception(status, new IllegalStateException());
	}
}