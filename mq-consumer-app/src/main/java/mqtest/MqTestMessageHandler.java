package mqtest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.ErrorHandler;

@Slf4j
public class MqTestMessageHandler implements ErrorHandler {

	public void handleMessage(String msg) {
		log.debug("received msg={}", msg);
	}

	@Override
	public void handleError(Throwable t) {
		log.error("error during receive msg, ex={}", t);
	}
}
