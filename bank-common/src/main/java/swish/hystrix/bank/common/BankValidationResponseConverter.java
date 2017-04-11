package swish.hystrix.bank.common;

public class BankValidationResponseConverter {

	public static String encode(BankValidationResponse r) {
		return r.getBankId() + "|" + 
			   r.getRequestId() + "|" + 
			   r.getResponse();
	}

	public static BankValidationResponse decode(String str) {
		String[] fields = str.split("\\|");
		BankValidationResponse resp = new BankValidationResponse();
		resp.setBankId(fields[0]);
		resp.setRequestId(fields[1]);
		resp.setResponse(fields[2]);
		return resp;
	}

}
