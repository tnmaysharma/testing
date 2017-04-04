package com.starterkit.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * @author 540497
 *
 */
@Component
public class RabbitMQMessagePublisher implements IMessagePublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/* 
	 * method to publish the json message in to rabbit MQ server and returns the unique id
	 * @see com.cognizant.starterkit.mq.IMessagePublisher#publishMessage(java.lang.String)
	 */
	@Override
	public void publishMessage(String messageContent,String requestGuid) {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setCorrelationIdString(requestGuid);
		messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		Message message = new Message(messageContent.getBytes(), messageProperties);
		rabbitTemplate.send(message);
	}

}
