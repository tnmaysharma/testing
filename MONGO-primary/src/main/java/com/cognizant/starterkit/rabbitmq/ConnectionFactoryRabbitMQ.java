package com.cognizant.starterkit.rabbitmq;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
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
	
	private String queueName;
	
	private String exchange;
	
	private String serviceName;
	/**
	 * ConnectionFactory class to make rabbitMQ connection
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@Bean
	public ConnectionFactory connectionFactory() throws ParseException {
		ConnectionFactory connectionFactory = null;
	/*	{
			CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(rabbitMQHost);
			cachingConnectionFactory.setPort(Integer.valueOf(rabbitMQPort));
			cachingConnectionFactory.setUsername(rabbitMQUserName);
			cachingConnectionFactory.setPassword(rabbitMQPassword);
			connectionFactory = cachingConnectionFactory;
		}*/
		String mqService =null;
		{
			CloudFactory cloudFactory = new CloudFactory();
			Cloud cloud = cloudFactory.getCloud();
			String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
			System.out.println(VCAP_SERVICES);
			String VCAP_SERVICES1 = "{ \"VCAP_SERVICES\": { \"Mongo-Service\": [ { \"credentials\": { \"DBName\": \"DB_3c7703f7_e795_4317_9fb1_a2909ae56028\", \"IP\": \"10.0.2.9\", \"password\": \"yjhiub2fdc\", \"port\": \"27017\", \"uri\": \"mongodb://YWNhNTFjZTYtOGYyNi00NTZjLTlkOTAtYTVmYTJiNjlkNDcy:yjhiub2fdc@10.0.2.9:27017/DB_3c7703f7_e795_4317_9fb1_a2909ae56028\", \"username\": \"YWNhNTFjZTYtOGYyNi00NTZjLTlkOTAtYTVmYTJiNjlkNDcy\" }, \"label\": \"Mongo-Service\", \"name\": \"mongo-bp\", \"plan\": \"Sandbox\", \"provider\": null, \"syslog_drain_url\": null, \"tags\": [ \"svc_offr_storage\" ], \"volume_mounts\": [] } ], \"RabbitMQ-Service\": [ { \"credentials\": {}, \"label\": \"RabbitMQ-Service\", \"name\": \"mq\", \"plan\": \"Sandbox\", \"provider\": null, \"syslog_drain_url\": null, \"tags\": [ \"svc_offr_devtools\" ], \"volume_mounts\": [] } ] } }";
			JSONObject VCAP_SERVICES_OBJECT= (JSONObject) new JSONParser().parse(VCAP_SERVICES);
			if(VCAP_SERVICES_OBJECT.containsKey("RabbitMQ-Service")){
				JSONArray rabbitArray = (JSONArray) VCAP_SERVICES_OBJECT.get("RabbitMQ-Service");
				for(Object obj : rabbitArray.toArray()){
					 mqService = (String) ((JSONObject)obj).get("name");
				}
			}
				
			
			
			System.out.println(mqService);
			if (mqService == null) {
				mqService = serviceName;
			}
			connectionFactory = cloud.getServiceConnector(mqService, ConnectionFactory.class, null);
		}
		return connectionFactory;
	}

	/**
	 * RabbitTemplate Instance will be created to send message
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@Bean
	public RabbitTemplate rabbitTemplate() throws ParseException {
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
