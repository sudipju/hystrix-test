package swish.hystrix;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class JmsConfiguration {
	@Bean
	public String jmsIncomingQueue() {
		String bankId = System.getProperty("bank.id", "BANK");		
		String queueName = System.getProperty("jms.queue.in", "queue.bank." + bankId + ".in");
		return queueName;
	}
	
	@Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //factory.setTransactionManager(transactionManager);
        factory.setConcurrency("5-10");
        //factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        //factory.setSessionTransacted(true);
        return factory;
    }
 
	@Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        return jmsTemplate;
    }
	
	/*
	@Bean
    public ConnectionFactory connectionFactory() {
        MQXAConnectionFactory factory = null;
        try {
            factory = new MQXAConnectionFactory();
            factory.setHostName(properties.getHost());
            factory.setPort(properties.getPort());
            factory.setQueueManager(properties.getQueueManager());
            factory.setChannel(properties.getChannel());
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
 
        return factory;
    }
    */
}
