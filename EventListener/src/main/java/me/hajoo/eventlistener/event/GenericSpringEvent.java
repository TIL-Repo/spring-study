package me.hajoo.eventlistener.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class GenericSpringEvent<T> implements ResolvableTypeProvider {

	private T what;
	private boolean success;

	public GenericSpringEvent(T what, boolean success) {
		this.what = what;
		this.success = success;
	}

	public T getWhat() {
		return what;
	}

	public boolean isSuccess() {
		return success;
	}

	@Override
	public ResolvableType getResolvableType() {
		return ResolvableType.forClassWithGenerics(
			getClass(),
			ResolvableType.forInstance(what)
		);
	}
}
