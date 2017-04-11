package swish.hystrix.bank.common;

public class BankValidationRequestConverter {

	public static String encode(BankValidationRequest req) {
		return req.getRequestId();
	}

	public static BankValidationRequest decode(String str) {
		String[] fields = str.split("\\|");
		return new BankValidationRequest(fields[0]);
	}

}
