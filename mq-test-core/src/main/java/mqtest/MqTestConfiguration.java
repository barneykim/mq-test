package mqtest;

import java.util.Map;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;

@Configuration
public class MqTestConfiguration {

	protected final String QUEUE_NAME = "mqtest.queue";

	protected final String MQ_SERVER = "localhost";
	protected final int MQ_PORT = 5672;
	protected final String MQ_USERNAME = "mqtest";
	protected final String MQ_PASSWORD = "mqtest";
	protected final String MQ_VIRTUALHOST = "/mqtest";

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory(MQ_SERVER, MQ_PORT);
		factory.setUsername(MQ_USERNAME);
		factory.setPassword(MQ_PASSWORD);
		factory.setVirtualHost(MQ_VIRTUALHOST);
		return factory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setRoutingKey(QUEUE_NAME);
		template.setQueue(QUEUE_NAME);
		return template;
	}

	@Bean
	public Queue testQueue() {
		Map<String, Object> arguments = Maps.newHashMap();
		arguments.put("x-ha-policy", "all");
		return new Queue(QUEUE_NAME, true, false, false, arguments);
	}
}
