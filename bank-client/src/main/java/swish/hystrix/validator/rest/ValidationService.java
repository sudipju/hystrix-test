package swish.hystrix.validator.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import rx.Observable;
import rx.functions.Func2;
import swish.hystrix.bank.common.BankValidationRequest;
import swish.hystrix.bank.common.BankValidationResponse;
import swish.hystrix.validator.client.BankServiceClient;
import swish.hystrix.validator.client.BankServiceClientWrapper;

@RestController
public class ValidationService {
	final static Logger log = Logger.getLogger(ValidationService.class);

	@Value("${rest.timeout:30000}")
	private long restTimeout;
	
	@Autowired
	private BankServiceClientWrapper bankService;

	@Autowired
	private IdService idService;
	
	public ValidationService() {
	}

	@RequestMapping(
			path = "/validate",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
			)
	public DeferredResult<ValidationResponse> validate(@RequestBody ValidationRequest request) {
		DeferredResult<ValidationResponse> deferred = new DeferredResult<>(restTimeout);
		Observable<BankValidationResponse> debetObs = sendDebetRequest(request);
		Observable<BankValidationResponse> creditObs = sendCreditRequest(request);
		Observable<ValidationResponse> obs = Observable.zip(debetObs, creditObs, 
				(debet,credit) -> createResponse(debet,credit));

		obs.subscribe(r -> deferred.setResult(r), 
				      e -> deferred.setErrorResult(e));
		return deferred;
	}

	private Observable<BankValidationResponse> sendDebetRequest(ValidationRequest request) {
		BankValidationRequest bvr = mapDebetRequest(request);
		return bankService.sendValidation(bvr);
	}

	private BankValidationRequest mapDebetRequest(ValidationRequest request) {
		BankValidationRequest bvr = new BankValidationRequest(generateRequestId());
		bvr.setBankId(request.getDebetBankId());
		return bvr;
	}

	private Observable<BankValidationResponse> sendCreditRequest(ValidationRequest request) {
		BankValidationRequest bvr = mapCreditRequest(request);
		return bankService.sendValidation(bvr);
	}

	private BankValidationRequest mapCreditRequest(ValidationRequest request) {
		BankValidationRequest bvr = new BankValidationRequest(generateRequestId());
		bvr.setBankId(request.getCreditBankId());
		return bvr;
	}

	private ValidationResponse createResponse(BankValidationResponse debet, BankValidationResponse credit) {
		ValidationResponse response = new ValidationResponse();
		response.setStatus(debet.getResponse());
		return response;
	}

	private ValidationResponse mapResponse(BankValidationResponse r) {
		ValidationResponse response = new ValidationResponse();
		response.setStatus(r.getResponse());
		return response;
	}

	private String generateRequestId() {
		return idService.next();
	}


}
