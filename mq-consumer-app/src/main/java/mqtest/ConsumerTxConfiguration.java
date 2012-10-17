package mqtest;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerTxConfiguration extends MqTestConfiguration {

	@Bean
	public RabbitTransactionManager rabbitTransactionManager() {
		return new RabbitTransactionManager(connectionFactory());
	}

	@Bean
	public SimpleMessageListenerContainer listenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		RabbitTransactionManager txManager = rabbitTransactionManager();
		container.setConnectionFactory(txManager.getConnectionFactory());
		container.setChannelTransacted(true);
		container.setQueueNames(QUEUE_NAME);
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		container.setMessageListener(new MessageListenerAdapter(new MqTestMessageHandler()));
		return container;
	}
}
