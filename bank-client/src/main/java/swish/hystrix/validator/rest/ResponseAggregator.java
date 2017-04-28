package swish.hystrix.validator.rest;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ResponseAggregator<T1,T2> {
	private T1 response1;
	private T2 response2;
	private Throwable e;
	private AtomicInteger completeCount = new AtomicInteger();
	private BiConsumer<T1,T2> consumer;
	private Consumer<Throwable> errorHandler;
	
	
	public ResponseAggregator(BiConsumer<T1, T2> consumer, Consumer<Throwable> errorHandler) {
		super();
		this.consumer = consumer;
		this.errorHandler = errorHandler;
	}

	void oneCompleted() {
		if (completeCount.incrementAndGet() >= 2) {
			completed();
		}
	}

	void complete1(T1 response1) {
		this.response1 = response1;
		oneCompleted();
	}
	
	void error(Throwable e) {
		this.e = e;
		oneCompleted();
	}
	
	void complete2(T2 response2) {
		this.response2 = response2;
		oneCompleted();
	}
	
	void completed() {
		if (response1 != null && response2 != null) {
			consumer.accept(response1, response2);
		} else {
			errorHandler.accept(e);
		}
	}
}
