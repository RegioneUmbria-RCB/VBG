package it.gruppoinit.pal.gp.core.features.rabbitmq.model.messaggi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class BodyMessaggioScadenza {

    private String uuid;
    private String uuidPratica;
    private String provenienza;
    private List<String> codiciFiscaliDestinatari;

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getUuidPratica() {

	return uuidPratica;
    }

    public void setUuidPratica(String uuidPratica) {

	this.uuidPratica = uuidPratica;
    }

    public String getProvenienza() {

	return provenienza;
    }

    public void setProvenienza(String provenienza) {

	this.provenienza = provenienza;
    }

    public List<String> getCodiciFiscaliDestinatari() {

	if (this.codiciFiscaliDestinatari == null) {
	    this.codiciFiscaliDestinatari = new ArrayList<String>();
	}
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