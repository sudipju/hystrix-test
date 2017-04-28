package swish.hystrix;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControlService {
	final static Logger log = Logger.getLogger(ControlService.class);

	@RequestMapping(
			path = "/control/switch-off",
			method = RequestMethod.GET)			
	public void switchOn() {
		System.setProperty("bank.service.disabled", "true");
		log.info("switched off");
	}

	@RequestMapping(
			path = "/control/switch-on",
			method = RequestMethod.GET)			
	public void switchOff() {
		System.setProperty("bank.service.disabled", "false");
		log.info("switched on");
	}

	@RequestMapping(
			path = "/control/delay/{delayMs}",
			method = RequestMethod.GET)			
	public void delay(@PathVariable String delayMs) {
		 System.setProperty("bank.service.delay", delayMs);
		 log.info("Delay set to: " + delayMs + " ms");
	}
	
	@RequestMapping(
			path = "/control/error/{errorRate}",
			method = RequestMethod.GET)			
	public void errorRate(@PathVariable String errorRate) {
		 System.setProperty("bank.service.error", errorRate);
		 log.info("Error rate set to: " + errorRate + "%");
	}


}
