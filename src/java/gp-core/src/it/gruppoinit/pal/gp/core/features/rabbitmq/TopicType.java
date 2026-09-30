package it.gruppoinit.pal.gp.core.features.rabbitmq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TopicType {

    @XmlElement(name = "topic")
    private String topic;

    public TopicType() {

	super();
    }

    public TopicType(String topic) {

	this();
	this.topic = topic;
    }

    public String getTopic() {

	return topic;
    }

    public void setTopic(String topic) {

	this.topic = topic;
    }
}
