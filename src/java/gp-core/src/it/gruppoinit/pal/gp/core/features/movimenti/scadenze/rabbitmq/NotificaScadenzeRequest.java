package it.gruppoinit.pal.gp.core.features.movimenti.scadenze.rabbitmq;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;

public class NotificaScadenzeRequest {

    private NotificaScadenzeRequest() {

	super();
    }

    public NotificaScadenzeRequest(Integer codiceMovimento, String software, String uuidPratica, String uuIdMovimento, String tipoMovimento,
	    RabbitTopicEnum topic, Integer codiceIstanza) {

	this();
	this.codiceIstanza = codiceIstanza;
	this.codiceMovimento = codiceMovimento;
	this.software = software;
	this.uuidPratica = uuidPratica;
	this.uuIdMovimento = uuIdMovimento;
	this.tipoMovimento = tipoMovimento;
	this.topic = topic;
    }

    private Integer codiceIstanza;
    private Integer codiceMovimento;
    private String software;
    private String uuidPratica;
    private String uuIdMovimento;
    private String tipoMovimento;
    private RabbitTopicEnum topic;

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
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

    public String getUuIdMovimento() {

	return uuIdMovimento;
    }

    public void setUuIdMovimento(String uuIdMovimento) {

	this.uuIdMovimento = uuIdMovimento;
    }

    public String getTipoMovimento() {

	return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {

	this.tipoMovimento = tipoMovimento;
    }

    public RabbitTopicEnum getTopic() {

	return topic;
    }

    public void setTopic(RabbitTopicEnum topic) {

	this.topic = topic;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
