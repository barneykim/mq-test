package mqtest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Consumer {

	public static void main(String[] args) {
		new AnnotationConfigApplicationContext(ConsumerTxConfiguration.class);
		log.debug("waiting for MQ...");
	}
}
