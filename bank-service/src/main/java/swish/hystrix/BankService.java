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
		
	    int errorPercentage = Integer.getInteger("bank.service.error", 0);
	    if (Math.random() < errorPercentage * 0.01) {
	    	throw new RuntimeException("Random exception");
	    }
	    
	    int delay = Integer.getInteger("bank.service.delay", 0);
		if (delay > 0) {
			sleepRandom(delay);
		}
		
		response.setBankId(bankId);
		response.setRequestId(request.getRequestId());
		response.setResponse("OK");
		return response;
	}

	private void sleepRandom(int delay) {
		int sleep = (int)(delay * Math.random());
		
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
		}
	}
}
