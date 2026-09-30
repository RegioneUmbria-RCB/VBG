package it.gruppoinit.pal.gp.core.features.rabbitmq.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
public class RichiestaPerTopic {

    @XmlElement
    private String topic;

    public String getTopic() {

	return topic;
    }

    public void setTopic(String topic) {

	this.topic = topic;
    }
}
