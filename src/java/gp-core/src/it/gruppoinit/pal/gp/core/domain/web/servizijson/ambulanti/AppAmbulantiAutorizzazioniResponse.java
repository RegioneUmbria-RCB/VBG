package it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppAmbulantiAutorizzazioniResponse {

    @XmlElement
    private List<FiltroSoggetti> soggetti;
    @XmlElement
    private List<AppAmbulantiAutorizzazione> autorizzazioni;

    public List<FiltroSoggetti> getSoggetti() {

	if (this.soggetti == null) {
	    this.soggetti = new ArrayList<FiltroSoggetti>();
	}
	return soggetti;
    }

    public void setSoggetti(List<FiltroSoggetti> soggetti) {

	this.soggetti = soggetti;
    }

    public List<AppAmbulantiAutorizzazione> getAutorizzazioni() {

	if (this.autorizzazioni == null) {
	    this.autorizzazioni = new ArrayList<AppAmbulantiAutorizzazione>();
	}
	return autorizzazioni;
    }

    public void setAutorizzazioni(List<AppAmbulantiAutorizzazione> autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }
}
