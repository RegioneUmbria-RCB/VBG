package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecuperaFileFirmatoResponse {

    @XmlElement(name = "guid")
    private String guid;
    @XmlElement(name = "codiceoggetto")
    private Integer codiceOggetto;
    @XmlElement(name = "nomefile")
    private String nomeFile;

    public String getGuid() {

	return guid;
    }

    public void setGuid(String guid) {

	this.guid = guid;
    }

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }
}
