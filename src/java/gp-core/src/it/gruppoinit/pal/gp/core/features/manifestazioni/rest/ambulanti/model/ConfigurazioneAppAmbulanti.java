package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class ConfigurazioneAppAmbulanti {

    @XmlElement(name = "borsellino")
    private ConfigurazioneBorsellino borsellino;
    @XmlElement(name = "autorizzazioni")
    private ConfigurazioneAutorizzazioniAppAmbulanti autorizzazioni;
    @XmlElement(name = "attiva_verifica_mail")
    private boolean attivaVerificaMail;
    @XmlElement(name = "etichette")
    private List<EtichettaApp> etichette;
    @XmlElement(name = "login_url")
    private String loginUrl;

    public ConfigurazioneBorsellino getBorsellino() {

	return borsellino;
    }

    public void setBorsellino(ConfigurazioneBorsellino borsellino) {

	this.borsellino = borsellino;
    }

    public List<EtichettaApp> getEtichette() {

	if (etichette == null) {
	    etichette = new ArrayList<EtichettaApp>();
	}
	return etichette;
    }

    public void setEtichette(List<EtichettaApp> etichette) {

	this.etichette = etichette;
    }

    public boolean getAttivaVerificaMail() {

	return attivaVerificaMail;
    }

    public void setAttivaVerificaMail(boolean attivaVerificaMail) {

	this.attivaVerificaMail = attivaVerificaMail;
    }

    public ConfigurazioneAutorizzazioniAppAmbulanti getAutorizzazioni() {

	return autorizzazioni;
    }

    public void setAutorizzazioni(ConfigurazioneAutorizzazioniAppAmbulanti autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    public void setLoginUrl(String loginUrl) {

	this.loginUrl = loginUrl;
    }

    public String getLoginUrl() {

	return loginUrl;
    }
}
