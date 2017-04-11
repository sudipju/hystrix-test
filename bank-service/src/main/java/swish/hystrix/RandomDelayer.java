package swish.hystrix;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class RandomDelayer {
	@Value("${delay.min:0}")
	private long min;
	
	@Value("${delay.max:0}")
	private long max;
	
	public void delay() {
		if (max > 0 && min >= 0 && max >= min) {
			long ms = min + (long)((max - min) * Math.random());
			try {
				Thread.sleep(ms);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
