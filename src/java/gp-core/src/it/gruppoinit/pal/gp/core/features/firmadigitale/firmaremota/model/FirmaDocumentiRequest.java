package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmaDocumentiRequest {

    @XmlElement(name = "sessionid")
    private String sessionId;
    @XmlElement(name = "idConfigurazione")
    private Integer idConfigurazione;
    @XmlElement(name = "parametri")
    private List<ConfigurazioneParametro> parametri;
    @XmlElement(name = "idoggetti")
    private List<Integer> idOggetti;

    public String getSessionId() {

	return sessionId;
    }

    public void setSessionId(String sessionId) {

	this.sessionId = sessionId;
    }

    public Integer getIdConfigurazione() {

	return idConfigurazione;
    }

    public void setIdConfigurazione(Integer idConfigurazione) {

	this.idConfigurazione = idConfigurazione;
    }

    public List<ConfigurazioneParametro> getParametri() {

	return parametri;
    }

    public void setParametri(List<ConfigurazioneParametro> parametri) {

	this.parametri = parametri;
    }

    public List<Integer> getIdOggetti() {

	return idOggetti;
    }

    public void setIdOggetti(List<Integer> idOggetti) {

	this.idOggetti = idOggetti;
    }
}
