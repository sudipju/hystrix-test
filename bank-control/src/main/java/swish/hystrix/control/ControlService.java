package swish.hystrix.control;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ControlService {
	@RequestMapping(
			path = "/control/{pid}/delay/{delayMs}",
			method = RequestMethod.GET)			
	public void delay(@PathVariable String pid, @PathVariable String delayMs) {
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.getForEntity(buildUrl(pid,"delay",delayMs), String.class);
		response.getStatusCode(); 
	}
	
	private String buildUrl(String pid, String function, String arg) {
		int port = 8090 + Integer.parseInt(pid);
		String url = "http://localhost:" + port + "/control/" + function;
		if (arg != null) url +=  "/" + arg;
		return url;
	}

	@RequestMapping(
			path = "/control/{pid}/error/{errorRate}",
			method = RequestMethod.GET)			
	public void errorRate(@PathVariable String pid, @PathVariable String errorRate) {
		RestTemplate rest = new RestTemplate();		
		ResponseEntity<String> response = rest.getForEntity(buildUrl(pid,"error",errorRate), String.class);
		response.getStatusCode(); 
	}

	@RequestMapping(
			path = "/control/{pid}/enable",
			method = RequestMethod.GET)			
	public void enable(@PathVariable String pid) {
		RestTemplate rest = new RestTemplate();		
		ResponseEntity<String> response = rest.getForEntity(buildUrl(pid,"switch-on",null), String.class);
		response.getStatusCode(); 
	}

	@RequestMapping(
			path = "/control/{pid}/disable",
			method = RequestMethod.GET)			
	public void disable(@PathVariable String pid) {
		RestTemplate rest = new RestTemplate();		
		ResponseEntity<String> response = rest.getForEntity(buildUrl(pid,"switch-off",null), String.class);
		response.getStatusCode(); 
	}

}
