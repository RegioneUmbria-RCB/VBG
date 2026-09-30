package it.gruppoinit.pal.gp.core.ws.client.parix;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "http://parixgate.infocamere.it/services/gate", name = "RicercaImpreseNonCessatePerCodiceFiscaleResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "ricercaImpreseNonCessatePerCodiceFiscaleReturn"
	})
public class RicercaImpreseNonCessatePerCodiceFiscaleResponseWrapper {

    public RicercaImpreseNonCessatePerCodiceFiscaleResponseWrapper() {

	super();
    }

    @XmlElement(namespace = "", name = "RicercaImpreseNonCessatePerCodiceFiscaleReturn", required = true)
    private String ricercaImpreseNonCessatePerCodiceFiscaleReturn;

    @XmlTransient
    public String getRicercaImpreseNonCessatePerCodiceFiscaleReturn() {

	return ricercaImpreseNonCessatePerCodiceFiscaleReturn;
    }

    public void setRicercaImpreseNonCessatePerCodiceFiscaleReturn(String ricercaImpreseNonCessatePerCodiceFiscaleReturn) {

	this.ricercaImpreseNonCessatePerCodiceFiscaleReturn = ricercaImpreseNonCessatePerCodiceFiscaleReturn;
    }
}
