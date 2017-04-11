package swish.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import swish.hystrix.bank.common.BankValidationRequest;
import swish.hystrix.bank.common.BankValidationResponse;

@Service
public class BankService {
	@Value("${bank.id}")
	String bankId;
	
	@Autowired
	RandomDelayer delayer;
	
	public BankValidationResponse validatePayment(BankValidationRequest request) {
		BankValidationResponse response = new BankValidationResponse();
		
		delayer.delay();
		
		response.setBankId(bankId);
		response.setRequestId(request.getRequestId());
		response.setResponse("OK");
		return response;
	}
}
