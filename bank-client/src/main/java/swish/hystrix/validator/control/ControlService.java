package swish.hystrix.validator.control;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.config.ConfigurationManager;

@RestController
public class ControlService {
	@RequestMapping(
			path = "/circuit-breaker/{cmdKey}/force-open",
			method = RequestMethod.GET)			
	public void forceOpen(@PathVariable String cmdKey) {
		ConfigurationManager.getConfigInstance().setProperty(
				getForceOpenProperty(cmdKey), true);
	}

	@RequestMapping(
			path = "/circuit-breaker/{cmdKey}/force-close",
			method = RequestMethod.GET)			
	public void forceClose(@PathVariable String cmdKey) {
		ConfigurationManager.getConfigInstance().setProperty(
				getForceCloseProperty(cmdKey), true);
	}

	@RequestMapping(
			path = "/circuit-breaker/{cmdKey}/reset",
			method = RequestMethod.GET)			
	public void reset(@PathVariable String cmdKey) {
		ConfigurationManager.getConfigInstance().setProperty(
				getForceOpenProperty(cmdKey), false);
		ConfigurationManager.getConfigInstance().setProperty(
				getForceCloseProperty(cmdKey), false);
	}

	@RequestMapping(
			path = "/circuit-breaker/{cmdKey}/enable",
			method = RequestMethod.GET)			
	public void enable(@PathVariable String cmdKey) {
		ConfigurationManager.getConfigInstance().setProperty(
				getCircuitBreaker(cmdKey) + ".circuitBreaker.enabled", true);
	}

	@RequestMapping(
			path = "/circuit-breaker/{cmdKey}/disable",
			method = RequestMethod.GET)			
	public void disable(@PathVariable String cmdKey) {
		ConfigurationManager.getConfigInstance().setProperty(
				getCircuitBreaker(cmdKey) + ".circuitBreaker.enabled", false);
	}
	
	private String getForceCloseProperty(String cmdKey) {
		return getCircuitBreaker(cmdKey) + ".circuitBreaker.forceClosed";
	}

	private String getForceOpenProperty(String cmdKey) {
		return getCircuitBreaker(cmdKey) + ".circuitBreaker.forceOpen";
	}
	
	private String getCircuitBreaker(String cmdKey) {
		return "hystrix.command." + cmdKey;
	}

}
