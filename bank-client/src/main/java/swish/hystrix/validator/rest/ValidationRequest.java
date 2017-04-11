package swish.hystrix.validator.rest;

public class ValidationRequest {
	private String debetBankId;
	private String debetAccount;
	private String creditBankId;
	private String creditAccount;
	public String getDebetBankId() {
		return debetBankId;
	}
	public void setDebetBankId(String debetBankId) {
		this.debetBankId = debetBankId;
	}
	public String getDebetAccount() {
		return debetAccount;
	}
	public void setDebetAccount(String debetAccount) {
		this.debetAccount = debetAccount;
	}
	public String getCreditBankId() {
		return creditBankId;
	}
	public void setCreditBankId(String creditBankId) {
		this.creditBankId = creditBankId;
	}
	public String getCreditAccount() {
		return creditAccount;
	}
	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}

	
	
}
