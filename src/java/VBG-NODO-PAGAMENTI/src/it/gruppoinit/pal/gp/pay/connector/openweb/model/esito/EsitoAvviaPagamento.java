package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "", propOrder = { "esito", //
	"errore", //
	"urlRedirect" //
})
public class EsitoAvviaPagamento {

    @XmlElement(name = "esito")
    private String esito;
    @XmlElement(name = "errore")
    private String errore;
    @XmlElement(name = "url_redirect")
    private String urlRedirect;

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

    public String getUrlRedirect() {

	return urlRedirect;
    }

    public void setUrlRedirect(String urlRedirect) {

	this.urlRedirect = urlRedirect;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
