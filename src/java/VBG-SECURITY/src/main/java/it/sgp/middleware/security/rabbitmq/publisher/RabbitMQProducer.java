package it.sgp.middleware.security.rabbitmq.publisher;

import java.io.IOException;


import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.AMQP.BasicProperties;

import it.sgp.middleware.security.rabbitmq.config.RabbitMQConfig;
import it.sgp.middleware.security.rabbitmq.modellazione.TopicEnum;


public class RabbitMQProducer {

    private RabbitMQConfig rabbitMQConfig;
    private String exchange;
    private TopicEnum topic;

    public RabbitMQProducer(RabbitMQConfig rabbitMQConfig, String exchange, TopicEnum topic) {

	super();
	this.rabbitMQConfig = rabbitMQConfig;
	this.exchange = exchange;
	this.topic = topic;
    }

    public void sendMessage(String messageId, String message) throws IOException, TimeoutException, Exception {	    	
	BasicProperties props = new BasicProperties();
	props = props.builder().deliveryMode(2).messageId(messageId).build();
	rabbitMQConfig.getChannelWrapper().basicPublish(this.exchange, this.topic.getValue(), props, message.getBytes(StandardCharsets.UTF_8));
    }
}
