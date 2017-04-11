package swish.hystrix;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import swish.hystrix.bank.common.BankValidationRequest;
import swish.hystrix.bank.common.BankValidationRequestConverter;
import swish.hystrix.bank.common.BankValidationResponse;
import swish.hystrix.bank.common.BankValidationResponseConverter;

@Service
public class BankJmsListener {
	final static Logger log = Logger.getLogger(BankJmsListener.class);
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	BankService bankService;
	
	@JmsListener(destination = "${jms.queue.in}", containerFactory = "defaultJmsListenerContainerFactory")
    public void onMessage(TextMessage message) throws JMSException {
        log.info("onMessage" + message);
        
        try {
        	BankValidationRequest bvr = BankValidationRequestConverter.decode(message.getText());
            BankValidationResponse reply = bankService.validatePayment(bvr);
            Destination replyTo = message.getJMSReplyTo();
            sendReply(replyTo, toMessage(reply), message.getJMSCorrelationID());
        } catch (JMSException e) {
            log.error("Cannot consume message", e);
        }
    }
 
	private String toMessage(BankValidationResponse reply) {
		return BankValidationResponseConverter.encode(reply);
	}

	public void sendReply(final Destination destination, final String message, final String correlationID) {
	    log.info("sendMessage:" + message);
	    jmsTemplate.convertAndSend(destination, message, 
	    	msg -> {
	    		msg.setJMSCorrelationID(correlationID);
				return msg;
			});
	}
}
