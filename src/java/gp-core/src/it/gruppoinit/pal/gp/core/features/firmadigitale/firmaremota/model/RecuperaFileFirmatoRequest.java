package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecuperaFileFirmatoRequest {

    @XmlElement(name = "idConfigurazione")
    private Integer idConfigurazione;
    @XmlElement(name = "sessionid")
    private String sessionId;
    @XmlElement(name = "codiceOggetto")
    private Integer codiceOggetto;

    public Integer getIdConfigurazione() {

	return idConfigurazione;
    }

    public void setIdConfigurazione(Integer idConfigurazione) {

	this.idConfigurazione = idConfigurazione;
    }

    public String getSessionId() {

	return sessionId;
    }

    public void setSessionId(String sessionId) {

	this.sessionId = sessionId;
    }

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }
}
