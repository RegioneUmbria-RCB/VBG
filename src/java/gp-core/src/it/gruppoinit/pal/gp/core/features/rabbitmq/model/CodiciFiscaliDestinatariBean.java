package it.gruppoinit.pal.gp.core.features.rabbitmq.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CodiciFiscaliDestinatariBean {

    @XmlElement(name = "codiciFiscaliDestinatari")
    private List<String> codiciFiscaliDestinatari;

    public List<String> getCodiciFiscaliDestinatari() {

	if (codiciFiscaliDestinatari == null) {
	    codiciFiscaliDestinatari = new ArrayList<String>();
	}
	return codiciFiscaliDestinatari;
    }

    public void setCodiciFiscaliDestinatari(List<String> codiciFiscaliDestinatari) {

	this.codiciFiscaliDestinatari = codiciFiscaliDestinatari;
    }
}
