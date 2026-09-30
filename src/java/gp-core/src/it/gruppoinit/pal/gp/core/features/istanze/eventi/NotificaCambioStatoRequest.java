package it.gruppoinit.pal.gp.core.features.istanze.eventi;

import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;

public class NotificaCambioStatoRequest {

    private Integer codiceIstanza;
    private String software;
    private String uuidPratica;
    private RabbitTopicEnum topicEnum;

    public NotificaCambioStatoRequest() {

	super();
    }

    public NotificaCambioStatoRequest(Integer codiceIstanza, String software, String uuidPratica, RabbitTopicEnum topic) {

	this.codiceIstanza = codiceIstanza;
	this.software = software;
	this.uuidPratica = uuidPratica;
	this.topicEnum = topic;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getUuidPratica() {

	return uuidPratica;
    }

    public void setUuidPratica(String uuidPratica) {

	this.uuidPratica = uuidPratica;
    }

    public RabbitTopicEnum getTopicEnum() {

	return topicEnum;
    }

    public void setTopicEnum(RabbitTopicEnum topicEnum) {

	this.topicEnum = topicEnum;
    }
}
