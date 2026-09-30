package it.gruppoinit.pal.gp.backoffice.web.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EsitoRestBean {

    @XmlElement
    private String codice;
    @XmlElement
    private String descrizione;

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
