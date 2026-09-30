package it.gruppoinit.pal.gp.core.features.rabbitmq.model.messaggi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class PraticheBodyMessaggi {

    private String uuid;
    private String provenienza;
    private List<String> codiciFiscaliDestinatari;

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getProvenienza() {

	return provenienza;
    }

    public void setProvenienza(String provenienza) {

	this.provenienza = provenienza;
    }

    public List<String> getCodiciFiscaliDestinatari() {

	return codiciFiscaliDestinatari;
    }

    public void setCodiciFiscaliDestinatari(List<String> codiciFiscaliDestinatari) {

	this.codiciFiscaliDestinatari = codiciFiscaliDestinatari;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}