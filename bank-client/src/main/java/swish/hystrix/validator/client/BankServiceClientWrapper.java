package swish.hystrix.validator.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rx.Observable;
import swish.hystrix.bank.common.BankValidationRequest;
import swish.hystrix.bank.common.BankValidationResponse;

@Service
public class BankServiceClientWrapper {
	@Autowired
	CircuitBreakers cb;
	
	public Observable<BankValidationResponse> sendValidation(BankValidationRequest request) {
		int routingCode = getRoutingCode(request.getBankId());
	    switch(routingCode) {
	    case 1: return cb.sendValidation1(request);
	    case 2: return cb.sendValidation2(request);
	    case 3: return cb.sendValidation3(request);
	    case 4: return cb.sendValidation4(request);
	    case 5: return cb.sendValidation5(request);
	    case 6: return cb.sendValidation6(request);
	    case 7: return cb.sendValidation7(request);
	    case 8: return cb.sendValidation8(request);
	    case 9: return cb.sendValidation9(request);
	    case 10:  return cb.sendValidation10(request);
	    }
		throw new IllegalArgumentException("No routing found for: " + request.getBankId());
	}

	private int getRoutingCode(String bankId) {
		if (bankId.toLowerCase().startsWith("bank")) {
			return Integer.parseInt(bankId.substring(4));
		}
		return -1;
	}

}
