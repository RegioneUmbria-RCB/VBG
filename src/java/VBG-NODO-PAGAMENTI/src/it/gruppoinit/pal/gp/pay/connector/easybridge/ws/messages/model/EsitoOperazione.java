package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;

public class EsitoOperazione {

    @XmlElement(name = "esitoOperazione")
    private String esitoOperazione;
    @XmlElement(name = "codiceErrore", required = false)
    private String codiceErrore;

    public String getEsitoOperazione() {

	return esitoOperazione;
    }

    public void setEsitoOperazione(String esitoOperazione) {

	this.esitoOperazione = esitoOperazione;
    }

    public String getCodiceErrore() {

	return codiceErrore;
    }

    public void setCodiceErrore(String codiceErrore) {

	this.codiceErrore = codiceErrore;
    }
}
