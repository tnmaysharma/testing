package com.starterkit.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.starterkit.Application;
import com.starterkit.utils.Utils;


@Configuration
public class ConnectionFactoryRabbitMQ {

	@Autowired
	private Utils utils;
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
	 * Loading all the required beans for this demo application
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(new Object[] { Application.class }, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	/**
	 * ConnectionFactory class to make rabbitMQ connection
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
	;
		String deployLocation = "cloud";
		ConnectionFactory connectionFactory = null;
		if (deployLocation.equalsIgnoreCase("cloud")) {
			CloudFactory cloudFactory = new CloudFactory();
			Cloud cloud = cloudFactory.getCloud();
			String mqService = null;
			if (mqService==null){
				mqService="mq";
			}
			connectionFactory = cloud.getServiceConnector(mqService, ConnectionFactory.class, null);
		} else {
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

	@Bean
	public MessageConverter jsonMessageConverter() {
		JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
		return jsonMessageConverter;
	}


    @Bean
    AmqpAdmin amqpAdmin(){
           RabbitAdmin admin = new RabbitAdmin(connectionFactory());
           admin.declareQueue(queue());
           admin.declareExchange(exchange());
           admin.declareBinding(binding(queue(),exchange()));
           return admin;
    }

	/**
	 * to create a queue instance based on the Queue name fetched from property
	 * files
	 * 
	 * @return
	 */
	@Bean
	Queue queue() {
		String queueName = utils.getVcapAppName(System.getenv("VCAP_APPLICATION")).concat(".queue");
		System.out.println(queueName + "name of the queue is");
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
		String exchange = utils.getVcapAppName(System.getenv("VCAP_APPLICATION")).concat(".exchange");
		System.out.println(exchange + "name of the exchange is");
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
		String queueName = utils.getVcapAppName(System.getenv("VCAP_APPLICATION")).concat(".queue");
		System.out.println(exchange + "name of the exchange sssis");
		System.out.println(queue + "name of the queue is ------------>>>>>>>>>>>");
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

}
