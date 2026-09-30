package it.gruppoinit.pal.gp.core.features.rabbitmq.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
public class MessaggiRabbitDaInviareRequest {

    @XmlElement
    private String topic;

    public String getTopic() {

	return topic;
    }

    public void setTopic(String topic) {

	this.topic = topic;
    }
}
