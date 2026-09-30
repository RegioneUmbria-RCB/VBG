package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ ConnettoreType.class })
public class ConnettoriListType {

    @XmlElement(name = "connettore")
    private ConnettoreType connettore;

    public ConnettoriListType() {

    }

    public ConnettoreType getConnettore() {

	return connettore;
    }

    public void setConnettore(ConnettoreType connettore) {

	this.connettore = connettore;
    }
}
