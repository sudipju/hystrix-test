package swish.hystrix.validator.client;

public interface BankValidationRequestListener {
	void onResponse(Object response);
	void onError(Throwable throwable);
}
