package it.gruppoinit.pal.gp.core.ws.client.parix;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "http://parixgate.infocamere.it/services/gate", name = "DettaglioCompletoImpresaResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "dettaglioCompletoImpresaReturn" })
public class DettaglioCompletoImpresaResponseWrapper {

    public DettaglioCompletoImpresaResponseWrapper() {

	super();
    }

    @XmlElement(namespace = "", name = "DettaglioCompletoImpresaReturn", required = true)
    private String dettaglioCompletoImpresaReturn;

    @XmlTransient
    public String getDettaglioCompletoImpresaReturn() {

	return dettaglioCompletoImpresaReturn;
    }

    public void setDettaglioCompletoImpresaReturn(String dettaglioCompletoImpresaReturn) {

	this.dettaglioCompletoImpresaReturn = dettaglioCompletoImpresaReturn;
    }
}
