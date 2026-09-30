package it.gruppoinit.pal.gp.core.features.rabbitmq.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CodiceFiscaleBean {

    public CodiceFiscaleBean() {

	super();
    }

    public CodiceFiscaleBean(String cf) {

	this();
	this.cf = cf;
    }

    @XmlElement
    private String cf;

    public String getCf() {

	return cf;
    }

    public void setCf(String cf) {

	this.cf = cf;
    }
}
