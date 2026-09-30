package it.sgp.middleware.security.rabbitmq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessaggiRabbitMQBroker<T> {

    private Header header;
    private T body;

    public Header getHeader() {

	return header;
    }

    public void setHeader(Header header) {

	this.header = header;
    }

    public T getBody() {

	return body;
    }

    public void setBody(T body) {

	this.body = body;
    }
}
