package swish.hystrix.validator.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import rx.AsyncEmitter;
import rx.Observable;
import swish.hystrix.bank.common.BankValidationRequest;
import swish.hystrix.bank.common.BankValidationRequestConverter;
import swish.hystrix.bank.common.BankValidationResponse;
import swish.hystrix.bank.common.BankValidationResponseConverter;

@Service
public class BankServiceClient {
	final static Logger log = Logger.getLogger(BankServiceClient.class);
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	Queue inQueue;
	
	@Autowired
	CacheService cache;
	
	private String correlationID = "abcd";
	
	@JmsListener(destination = "${jms.queue.in}", containerFactory = "defaultJmsListenerContainerFactory")
    public void onMessage(Message message) throws JMSException {
        
        try {
            BankValidationResponse response = toResponse((TextMessage)message);
            cache.getActiveRequest(response.getRequestId()).ifPresent(req -> {
            	req.getListener().onResponse(response);
            });
        } catch (JMSException e) {
            log.error("Cannot consume message", e);
        }
    }

	private BankValidationResponse toResponse(TextMessage message) throws JMSException {
		return BankValidationResponseConverter.decode(message.getText());
	}

	public void sendJmsRequest(final String destination, final BankValidationRequest req, final String correlationID) {
	    log.info("sendMessage:" + req);
	    
	    jmsTemplate.convertAndSend(destination, BankValidationRequestConverter.encode(req),
	    	msg -> {
	    		msg.setJMSCorrelationID(correlationID);
	    		msg.setJMSReplyTo(inQueue);
				return msg;
			});
	}
	
	public Observable<BankValidationResponse> sendValidation(BankValidationRequest request) {
		Observable<BankValidationResponse> obs = 
		    Observable.fromEmitter(emitter -> 
	        { 
	        	BankValidationRequestListener listener = new BankValidationRequestListener() {
					@Override
					public void onResponse(Object response) {
						emitter.onNext((BankValidationResponse)response);
						emitter.onCompleted();
					}

					@Override
					public void onError(Throwable throwable) {
						emitter.onError(throwable);
					} 
	        	};
	            sendRequest(request, listener);
	            emitter.setCancellation(() -> cancel(request));
	       }, AsyncEmitter.BackpressureMode.BUFFER);  		
		return obs;
	}

	
	private void cancel(BankValidationRequest request) {
		log.info("Request cancelled:" + request.getRequestId());
	}

	private void sendRequest(BankValidationRequest request, BankValidationRequestListener listener) {
		String destination = resolveDestination(request.getBankId());
		cache.putActiveRequest(new ActiveRequest(request,listener));
		sendJmsRequest(destination, request, correlationID);
	}

	private String resolveDestination(String bankId) {
		return "queue.bank." + bankId + ".in";
	}
}
