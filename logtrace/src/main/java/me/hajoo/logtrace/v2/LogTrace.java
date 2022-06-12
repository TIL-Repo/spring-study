package me.hajoo.logtrace.v2;

import me.hajoo.logtrace.common.trace.TraceStatus;

public interface LogTrace {

	TraceStatus begin(String message);

	void end(TraceStatus status);

	void exception(TraceStatus status, Exception e);
}
