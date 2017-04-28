package swish.hystrix.validator.circuitbreaker.notify;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;

@Service
public class CircuitBreakerHystrixEventNotifier extends HystrixEventNotifier {
	final static Logger log = Logger.getLogger(CircuitBreakerHystrixEventNotifier.class);

	public CircuitBreakerHystrixEventNotifier(){

    }

   public void markEvent(HystrixEventType eventType, HystrixCommandKey key) {
        log.info("EventType:" + eventType + " " + eventType.isTerminal() + " command:" + key);
    }
   
   @PostConstruct
   public void init() {
	   HystrixPlugins.getInstance().registerEventNotifier(this);
   }
}
