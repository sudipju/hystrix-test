package swish.hystrix.bank.common;

public class BankValidationRequest {
	private String requestId;
	private String bankId;
	
	public BankValidationRequest(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
}
