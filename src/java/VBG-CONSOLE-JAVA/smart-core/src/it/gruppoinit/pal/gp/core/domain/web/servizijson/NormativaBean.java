package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "normativa")
public class NormativaBean {

    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "tipologia")
    private String tipologia;
    @XmlElement(name = "link")
    private String link;
    @XmlElement(name = "codice_oggetto")
    private String codiceOggetto;

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getTipologia() {

	return tipologia;
    }

    public void setTipologia(String tipologia) {

	this.tipologia = tipologia;
    }

    public String getLink() {

	return link;
    }

    public void setLink(String link) {

	this.link = link;
    }

    public String getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(String codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }
}
