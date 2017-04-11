package swish.hystrix.validator;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@EnableJms
@SpringBootApplication
public class BankValidationApplication {
	@Bean
	public RestTemplate rest(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Value("${jms.queue.in:queue.validate.in}")
	private String inQueueName;
	
	@Autowired 
	private Environment env;
	
	@Bean
	public Queue inQueue() {
		return new ActiveMQQueue(inQueueName);
	}
	
	/*
	@Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory ccf = new CachingConnectionFactory(
                new ActiveMQConnectionFactory(
                		env.getProperty("spring.activemq.user"),
                		env.getProperty("spring.activemq.password"),
                		env.getProperty("spring.activemq.broker-url")
                ));
        ccf.setCacheProducers(true);
        ccf.setCacheConsumers(false);
        return ccf;
    } */
	
	@Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("10-20");
        //factory.setCacheLevelName("CACHE_SESSION");
        return factory;
    }

	public static void main(String[] args) {
		SpringApplication.run(BankValidationApplication.class, args);
	}
}
