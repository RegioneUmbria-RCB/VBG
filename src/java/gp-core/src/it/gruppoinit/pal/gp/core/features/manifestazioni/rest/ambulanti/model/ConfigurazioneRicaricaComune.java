package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ConfigurazioneRicaricaComune {

    @XmlElement
    private String codice;
    @XmlElement
    private String comune;
    @XmlElement
    private boolean ricaricabile;
    @XmlElement
    private List<InformativeAbbonamento> informative;
    @XmlElement(name = "pagamento_libero")
    private ConfigurazioneRicaricaComuneImportoLibero pagamentoLibero;
    @XmlElement(name = "tagli_ricariche")
    private List<TagliRicaricaAbbonamento> tagliRicariche;
    @XmlElement(name = "messaggio_non_ricaricabile")
    private String messaggioNonRicaricabile;

    public ConfigurazioneRicaricaComune() {

	super();
    }

    public ConfigurazioneRicaricaComune(String codice, String comune) {

	this.codice = codice;
	this.comune = comune;
    }

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public boolean isRicaricabile() {

	return ricaricabile;
    }

    public void setRicaricabile(boolean ricaricabile) {

	this.ricaricabile = ricaricabile;
    }

    public List<InformativeAbbonamento> getInformative() {

	if (this.informative == null) {
	    this.informative = new ArrayList<InformativeAbbonamento>();
	}
	return informative;
    }

    public ConfigurazioneRicaricaComuneImportoLibero getPagamentoLibero() {

	return pagamentoLibero;
    }

    public void setPagamentoLibero(ConfigurazioneRicaricaComuneImportoLibero pagamentoLibero) {

	this.pagamentoLibero = pagamentoLibero;
    }

    public void setInformative(List<InformativeAbbonamento> informative) {

	this.informative = informative;
    }

    public List<TagliRicaricaAbbonamento> getTagliRicariche() {

	if (this.tagliRicariche == null) {
	    this.tagliRicariche = new ArrayList<TagliRicaricaAbbonamento>();
	}
	return tagliRicariche;
    }

    public void setTagliRicariche(List<TagliRicaricaAbbonamento> tagliRicariche) {

	this.tagliRicariche = tagliRicariche;
    }

    public String getMessaggioNonRicaricabile() {

	return messaggioNonRicaricabile;
    }

    public void setMessaggioNonRicaricabile(String messaggioNonRicaricabile) {

	this.messaggioNonRicaricabile = messaggioNonRicaricabile;
    }
}
