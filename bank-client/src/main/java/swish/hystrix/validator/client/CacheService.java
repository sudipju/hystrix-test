package swish.hystrix.validator.client;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
	final static Logger log = Logger.getLogger(CacheService.class);

	private Map<String,ActiveRequest> activeRequests = new ConcurrentHashMap<>();

	Optional<ActiveRequest> getActiveRequest(String requestId) {
		ActiveRequest request = activeRequests.remove(requestId);
		if (request == null)
		{
			log.error("Request not found:" + requestId);
		}
		return Optional.ofNullable(request);
	}

	void putActiveRequest(ActiveRequest req) {
		activeRequests.put(req.getRequest().getRequestId(), req);
	}
}
