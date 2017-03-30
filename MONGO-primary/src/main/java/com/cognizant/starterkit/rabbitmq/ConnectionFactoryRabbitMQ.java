package com.cognizant.starterkit.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConnectionFactoryRabbitMQ {
	/**
	 * Method to start the spring boot for starter lit application
	 * 
	 * @param args
	 */
	@Value("${rabbitmq.host}")
	private String rabbitMQHost;

	@Value("${rabbitmq.port}")
	private String rabbitMQPort;

	@Value("${rabbitmq.username}")
	private String rabbitMQUserName;

	@Value("${rabbitmq.password}")
	private String rabbitMQPassword;

	@Value("${rabbitmq.queuename}")
	private String queueName;

	@Value("${rabbitmq.exchange}")
	private String exchange;

	@Value("${rabbitmq.service.name}")
	private String serviceName;
	/**
	 * ConnectionFactory class to make rabbitMQ connection
	 * 
	 * @return
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory connectionFactory = null;
		  {
				CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(rabbitMQHost);
				cachingConnectionFactory.setPort(Integer.valueOf(rabbitMQPort));
				cachingConnectionFactory.setUsername(rabbitMQUserName);
				cachingConnectionFactory.setPassword(rabbitMQPassword);
				connectionFactory = cachingConnectionFactory;
			}
		return connectionFactory;
	}

	/**
	 * RabbitTemplate Instance will be created to send message
	 * 
	 * @return
	 */
	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	/**
	 * MessageConverter instance to convert the Java Object in Json Object
	 * 
	 * @return
	 */
	@Bean
	public MessageConverter jsonMessageConverter() {
		JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
		return jsonMessageConverter;
	}

	/**
	 * to create a queue instance based on the Queue name fetched from property
	 * files
	 * 
	 * @return
	 */
	@Bean
	Queue queue() {
		return new Queue(queueName, true);
	}

	/**
	 * to create a Direct Exchange instance which will send the message based on
	 * the router key
	 * 
	 * @return
	 */
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	/**
	 * will create a binding instance for queue and exchange based on the router
	 * key
	 * 
	 * @param queue
	 * @param exchange
	 * @return
	 */
	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

}
