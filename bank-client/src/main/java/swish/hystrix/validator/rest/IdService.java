package swish.hystrix.validator.rest;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class IdService {
	private static final AtomicLong counter = new AtomicLong();
	
	public String next() {
		return Long.toHexString(counter.incrementAndGet());
	}
}
