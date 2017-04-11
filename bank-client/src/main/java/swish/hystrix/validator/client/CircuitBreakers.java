package swish.hystrix.validator.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import rx.Observable;
import swish.hystrix.bank.common.BankValidationRequest;
import swish.hystrix.bank.common.BankValidationResponse;

// This is a hack to get around the limitiation in javanica 
// that only allows constant HystrixCommandKeys. Should be replaced by direct
// configuration of HystrixCommands.
@Service
public class CircuitBreakers {
	@Autowired
	BankServiceClient delegate;
	
	@HystrixCommand(commandKey = "CircuitBreaker1", 
	        commandProperties = {
	                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
	        }) 
	public Observable<BankValidationResponse> sendValidation1(BankValidationRequest request) {
		return delegate.sendValidation(request);
	}

	@HystrixCommand(commandKey = "CircuitBreaker2", 
	        commandProperties = {
	                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
	        }) 
	public Observable<BankValidationResponse> sendValidation2(BankValidationRequest request) {
		return delegate.sendValidation(request);
	}
	@HystrixCommand(commandKey = "CircuitBreaker3", 
	        commandProperties = {
	                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
	        }) 
	public Observable<BankValidationResponse> sendValidation3(BankValidationRequest request) {
		return delegate.sendValidation(request);
	}
	@HystrixCommand(commandKey = "CircuitBreaker4", 
	        commandProperties = {
	                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
	        }) 
	public Observable<BankValidationResponse> sendValidation4(BankValidationRequest request) {
		return delegate.sendValidation(request);
	}
	@HystrixCommand(commandKey = "CircuitBreaker5", 
	        commandProperties = {
	                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
	        }) 
	public Observable<BankValidationResponse> sendValidation5(BankValidationRequest request) {
		return delegate.sendValidation(request);
	}
	@HystrixCommand(commandKey = "CircuitBreaker6", 
	        commandProperties = {
	                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
	        }) 
	public Observable<BankValidationResponse> sendValidation6(BankValidationRequest request) {
		return delegate.sendValidation(request);
	}
	@HystrixCommand(commandKey = "CircuitBreaker7", 
	        commandProperties = {
	                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
	        }) 
	public Observable<BankValidationResponse> sendValidation7(BankValidationRequest request) {
		return delegate.sendValidation(request);
	}
	
	@HystrixCommand(commandKey = "CircuitBreaker8", 
	        commandProperties = {
	                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
	        }) 
	public Observable<BankValidationResponse> sendValidation8(BankValidationRequest request) {
		return delegate.sendValidation(request);
	}
	
	@HystrixCommand(commandKey = "CircuitBreaker9", 
	        commandProperties = {
	                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
	        }) 
	public Observable<BankValidationResponse> sendValidation9(BankValidationRequest request) {
		return delegate.sendValidation(request);
	}
	
	@HystrixCommand(commandKey = "CircuitBreaker10", 
	        commandProperties = {
	                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
	        }) 
	public Observable<BankValidationResponse> sendValidation10(BankValidationRequest request) {
		return delegate.sendValidation(request);
	}
	
}
