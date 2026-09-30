package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "", propOrder = { "esito", //
	"url_avviso" //
})
public class EsitoScaricaAvviso {

    @XmlElement(name = "esito")
    private String esito;
    @XmlElement(name = "url_avviso")
    private String urlAvviso;

    public String getEsito() {

	return esito;
    }

    public void setEsito(String esito) {

	this.esito = esito;
    }

    public String getUrlAvviso() {

	return urlAvviso;
    }

    public void setUrlAvviso(String urlAvviso) {

	this.urlAvviso = urlAvviso;
    }
}
