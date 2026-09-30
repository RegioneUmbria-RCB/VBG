package it.gruppoinit.pal.gp.core.features.rabbitmq;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class TopicTypeList {

    @XmlElement(name = "topics")
    private List<TopicType> topics;

    public TopicTypeList() {

	super();
    }

    public List<TopicType> getTopics() {

	return topics;
    }

    public void setTopics(List<TopicType> topics) {

	this.topics = topics;
    }
}
