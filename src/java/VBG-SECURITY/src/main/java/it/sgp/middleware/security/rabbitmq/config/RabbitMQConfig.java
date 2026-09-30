package it.sgp.middleware.security.rabbitmq.config;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConfig {

    private ConnectionFactory connectionFactory;

    public RabbitMQConfig(String host, Integer port, String userName, String password) throws IOException, TimeoutException {

	super();
	connectionFactory = new ConnectionFactory();
	connectionFactory.setHost(host);
	connectionFactory.setPort(port);
	connectionFactory.setUsername(userName);
	connectionFactory.setPassword(password);
    }

    public RabbitMQChannelWrapper getChannelWrapper() throws IOException, TimeoutException {

	return new RabbitMQChannelWrapper(connectionFactory);
    }
}
