package it.gruppoinit.pal.gp.core.features.rabbitmq.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
public class TestiMovimentiRabbitRequest {

    @XmlElement
    private String uuidMovimento;
    @XmlElement
    private String topic;

    public String getUuidMovimento() {

	return uuidMovimento;
    }

    public void setUuidMovimento(String uuidMovimento) {

	this.uuidMovimento = uuidMovimento;
    }

    public String getTopic() {

	return topic;
    }

    public void setTopic(String topic) {

	this.topic = topic;
    }
}
