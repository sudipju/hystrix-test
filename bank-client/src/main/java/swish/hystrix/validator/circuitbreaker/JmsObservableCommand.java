package swish.hystrix.validator.circuitbreaker;

import java.util.function.Supplier;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;

import rx.Observable;

public class JmsObservableCommand<R> extends HystrixObservableCommand<R> {
	private static final HystrixCommandGroupKey GROUP_KEY = HystrixCommandGroupKey.Factory.asKey("JmsCircuitBreaker");
	private final Supplier<Observable<R>> supplier;
	
	public JmsObservableCommand(String commandKey, Supplier<Observable<R>> supplier) {
        super(HystrixObservableCommand.Setter
        		.withGroupKey(GROUP_KEY)
        		.andCommandKey(HystrixCommandKey.Factory.asKey(commandKey)));
        this.supplier = supplier;
    }
	
	@Override
	protected Observable<R> construct() {
		return supplier.get();
	}
}
