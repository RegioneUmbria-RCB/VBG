package it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FiltroSoggetti {

    @XmlElement
    private String cf;
    @XmlElement
    private String nominativo;
    @XmlElement
    private boolean titolarita;

    public String getCf() {

	return cf;
    }

    public void setCf(String cf) {

	this.cf = cf;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public boolean isTitolarita() {

	return titolarita;
    }

    public void setTitolarita(boolean titolarita) {

	this.titolarita = titolarita;
    }
}
