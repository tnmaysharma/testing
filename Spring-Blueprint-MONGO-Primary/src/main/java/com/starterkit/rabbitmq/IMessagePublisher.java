package com.starterkit.rabbitmq;


/**
 * @author 540497
 *
 */
public interface IMessagePublisher {

	public void publishMessage(String messageContent,String requestGuid);
}
