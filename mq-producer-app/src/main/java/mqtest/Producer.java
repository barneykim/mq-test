package mqtest;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Producer {

	public static void main(String[] args) throws Exception {
		log.debug("len={}", args.length);
		if (args.length == 0) {
			log.error("Usage: mq-producer-app name [times] [msg]");
			System.exit(1);
		}
		int times = 5;
		if (args.length == 2) {
			times = Integer.parseInt(args[1]);
		}
		String userMsg = null;
		if (args.length == 3) {
			userMsg = args[2];
		}
		ApplicationContext context = new AnnotationConfigApplicationContext(MqTestConfiguration.class);
		AmqpTemplate template = context.getBean(AmqpTemplate.class);

		String name = args[0];
		for (int i = 0; i < times; i++) {
			String s = userMsg;
			if (userMsg == null) {
				s = new Date().toString();
			}
			String msg = new StringBuilder(name).append(" : ").append(s).toString();

			template.convertAndSend(msg);
			log.debug("send No.{} msg={}", i, msg);
			Thread.sleep(1000);
		}
		System.exit(0);
	}
}
