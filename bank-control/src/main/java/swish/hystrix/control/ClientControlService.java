package swish.hystrix.control;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientControlService {
	@RequestMapping(
			path = "/circuit-breaker/{id}/force-open",
			method = RequestMethod.GET)			
	public void forceOpen(@PathVariable String id) {
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.getForEntity(buildUrl(id,"force-open"), String.class);
		response.getStatusCode(); 
	}
	
	@RequestMapping(
			path = "/circuit-breaker/{id}/force-close",
			method = RequestMethod.GET)			
	public void forceClose(@PathVariable String id) {
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.getForEntity(buildUrl(id,"force-close"), String.class);
		response.getStatusCode(); 
	}
	@RequestMapping(
			path = "/circuit-breaker/{id}/reset",
			method = RequestMethod.GET)			
	public void reset(@PathVariable String id) {
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.getForEntity(buildUrl(id,"reset"), String.class);
		response.getStatusCode(); 
	}
	@RequestMapping(
			path = "/circuit-breaker/{id}/enable",
			method = RequestMethod.GET)			
	public void enable(@PathVariable String id) {
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.getForEntity(buildUrl(id,"enable"), String.class);
		response.getStatusCode(); 
	}
	@RequestMapping(
			path = "/circuit-breaker/{id}/disable",
			method = RequestMethod.GET)			
	public void disable(@PathVariable String id) {
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.getForEntity(buildUrl(id,"disable"), String.class);
		response.getStatusCode(); 
	}
	
	private String buildUrl(String id, String function) {
		int port = 8080;
		String url = "http://localhost:" + port + "/circuit-breaker/" + id + "/" + function;
		return url;
	}


}
