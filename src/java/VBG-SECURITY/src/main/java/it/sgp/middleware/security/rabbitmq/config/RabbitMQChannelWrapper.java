package it.sgp.middleware.security.rabbitmq.config;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQChannelWrapper {

    private ConnectionFactory connectionFactory;

    public RabbitMQChannelWrapper(ConnectionFactory connectionFactory) throws IOException, TimeoutException {

	this.connectionFactory = connectionFactory;
    }

    public String getVersion() throws IOException, TimeoutException {

	Channel channel = connectionFactory.newConnection(RabbitMQConstants.CONNECTION_NAME_PUBLISHER).createChannel();
	try {
	    return channel.getConnection().getServerProperties().get("version").toString();
	} finally {
	    channel.close();
	    channel.getConnection().close();
	}
    }

    public void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException, TimeoutException {

	Channel channel = connectionFactory.newConnection(RabbitMQConstants.CONNECTION_NAME_PUBLISHER).createChannel();
	try {
	    channel.basicPublish(exchange, routingKey, props, body);
	} finally {
	    channel.close();
	    channel.getConnection().close();
	}
    }
}
