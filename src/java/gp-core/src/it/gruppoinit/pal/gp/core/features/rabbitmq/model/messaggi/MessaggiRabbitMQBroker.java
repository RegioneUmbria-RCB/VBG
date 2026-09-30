package it.gruppoinit.pal.gp.core.features.rabbitmq.model.messaggi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.features.rabbitmq.EnvVariables;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ PraticheBodyMessaggi.class, ComunicazioniUtenteBodyMessaggi.class, BodyMessaggioScadenza.class })
public class MessaggiRabbitMQBroker<T> {

    private Header header;
    @XmlAnyElement(lax = true)
    private T body;

    private MessaggiRabbitMQBroker() {

	super();
    }

    public MessaggiRabbitMQBroker(String alias, String software) {

	this();
	this.header = Header.fromVariables(alias, software);
    }

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

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
