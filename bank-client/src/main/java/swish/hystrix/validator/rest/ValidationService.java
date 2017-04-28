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
import swish.hystrix.validator.circuitbreaker.JmsObservableCommand;
import swish.hystrix.validator.client.BankServiceClient;
import swish.hystrix.validator.client.BankServiceClientWrapper;

@RestController
public class ValidationService {
	final static Logger log = Logger.getLogger(ValidationService.class);

	@Value("${rest.timeout:30000}")
	private long restTimeout;
	
	@Autowired
	private BankServiceClient bankService;

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
		Observable<BankValidationResponse> creditObs = sendCreditRequest(request);
		Observable<BankValidationResponse> debitObs = sendDebetRequest(request);
		
		/*
		Observable<ValidationResponse> obs = Observable.zip(creditObs, debitObs, 
				(debet,credit) -> createResponse(debet,credit));
		obs.subscribe(r -> deferred.setResult(r), 
				      e -> deferred.setErrorResult(e));
		*/
	
		ResponseAggregator<BankValidationResponse, BankValidationResponse> aggr = new ResponseAggregator<>(
				(debit,credit) -> deferred.setResult(createResponse(debit,credit)), 
				e -> deferred.setErrorResult(e));
		debitObs.subscribe(result -> aggr.complete1(result), err -> aggr.error(err));
		creditObs.subscribe(result -> aggr.complete2(result), err -> aggr.error(err));
		return deferred;
	}

	private Observable<BankValidationResponse> sendDebetRequest(ValidationRequest request) {
		BankValidationRequest bvr = mapDebetRequest(request);
		return 
				new JmsObservableCommand<BankValidationResponse>(bvr.getBankId(), 
						() -> bankService.sendValidation(bvr)).observe();
	}

	private BankValidationRequest mapDebetRequest(ValidationRequest request) {
		BankValidationRequest bvr = new BankValidationRequest(generateRequestId());
		bvr.setBankId(request.getDebetBankId());
		return bvr;
	}

	private Observable<BankValidationResponse> sendCreditRequest(ValidationRequest request) {
		BankValidationRequest bvr = mapCreditRequest(request);
		return 
			new JmsObservableCommand<BankValidationResponse>(bvr.getBankId(), 
					() -> bankService.sendValidation(bvr)).observe();
	}

	private BankValidationRequest mapCreditRequest(ValidationRequest request) {
		BankValidationRequest bvr = new BankValidationRequest(generateRequestId());
		bvr.setBankId(request.getCreditBankId());
		return bvr;
	}

	private ValidationResponse createResponse(BankValidationResponse debet, BankValidationResponse credit) {
		ValidationResponse response = new ValidationResponse();
		String creditResponse = credit.getResponse();
		String debetResponse = debet.getResponse();
		if ("OK".equals(creditResponse) && "OK".equals(debetResponse)) {
			response.setStatus("OK");
		} else {
			response.setStatus("ERROR");
		}
		log.info("Creating response:" + debet.getRequestId() + " " + credit.getRequestId());
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
