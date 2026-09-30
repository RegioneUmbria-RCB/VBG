package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "download")
public class DownloadBean {

    @XmlElement(name = "codice_oggetto")
    private String codiceOggetto;
    @XmlElement(name = "formato")
    private String formato;

    public String getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(String codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    public String getFormato() {

	return formato;
    }

    public void setFormato(String formato) {

	this.formato = formato;
    }
}
