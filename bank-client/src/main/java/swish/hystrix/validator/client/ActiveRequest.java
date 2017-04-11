package swish.hystrix.validator.client;

import swish.hystrix.bank.common.BankValidationRequest;

public class ActiveRequest {
	private final BankValidationRequest request;
	private final BankValidationRequestListener listener;
		
	public ActiveRequest(BankValidationRequest request, BankValidationRequestListener listener) {
		super();
		this.request = request;
		this.listener = listener;
	}
	public BankValidationRequest getRequest() {
		return request;
	}
	public BankValidationRequestListener getListener() {
		return listener;
	}
	public String getId() {
		return request.getRequestId();
	}
	
}
