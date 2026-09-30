package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "comunicazione")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComunicazioneAccountMailModel {

    @XmlElement(name = "account")
    private String account;
    @XmlElement(name = "template")
    private String template;

    public ComunicazioneAccountMailModel() {

	super();
    }

    public ComunicazioneAccountMailModel(String account, String template) {

	super();
	this.account = account;
	this.template = template;
    }

    public String getAccount() {

	return account;
    }

    public void setAccount(String account) {

	this.account = account;
    }

    public String getTemplate() {

	return template;
    }

    public void setTemplate(String template) {

	this.template = template;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }
}
