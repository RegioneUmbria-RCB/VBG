package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "", propOrder = { "esito", //
	"errore" //
})
public class EsitoCancellazione {

    @XmlElement(name = "esito")
    private String esito;
    @XmlElement(name = "errore")
    private String errore;

    public String getEsito() {

	return esito;
    }

    public void setEsito(String esito) {

	this.esito = esito;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }
}
