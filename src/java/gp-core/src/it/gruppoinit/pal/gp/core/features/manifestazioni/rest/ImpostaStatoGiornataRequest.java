package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImpostaStatoGiornataRequest {

    @XmlElement(name = "giornataChiusa")
    private Boolean giornataChiusa;

    public Boolean getGiornataChiusa() {

	return giornataChiusa;
    }

    public void setGiornataChiusa(Boolean giornataChiusa) {

	this.giornataChiusa = giornataChiusa;
    }
}
