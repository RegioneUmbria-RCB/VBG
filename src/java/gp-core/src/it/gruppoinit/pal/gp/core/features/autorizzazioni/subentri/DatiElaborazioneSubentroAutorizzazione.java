package it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;

public class DatiElaborazioneSubentroAutorizzazione {

    private Integer idAutorizzazione;
    private String estremiAutorizzazione;
    private boolean concessione;
    private List<EsitoElaborazioneEvento> esiti;

    private DatiElaborazioneSubentroAutorizzazione() {

	super();
    }

    public DatiElaborazioneSubentroAutorizzazione(Autorizzazioni autorizzazione, AutorizzazioniConcessioni autConc) {

	this();
	this.idAutorizzazione = autorizzazione.getId().getCodice();
	this.estremiAutorizzazione = autorizzazione.getTransientEstremiAut();
	if (autConc != null) {
	    this.concessione = true;
	    this.estremiAutorizzazione = autConc.getTransientEstremiConcessione();
	}
    }

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }

    public String getEstremiAutorizzazione() {

	return estremiAutorizzazione;
    }

    public boolean isConcessione() {

	return concessione;
    }

    public List<EsitoElaborazioneEvento> getEsiti() {

	if (this.esiti == null) {
	    esiti = new ArrayList<EsitoElaborazioneEvento>();
	}
	return esiti;
    }

    public boolean isErroreOWarning() {

	for (EsitoElaborazioneEvento esito : getEsiti()) {
	    if (esito.isErroreOWarning()) {
		return true;
	    }
	}
	return false;
    }

    public boolean isErrore() {

	for (EsitoElaborazioneEvento esito : getEsiti()) {
	    if (esito.isErrore()) {
		return true;
	    }
	}
	return false;
    }

    public boolean isWarning() {

	for (EsitoElaborazioneEvento esito : getEsiti()) {
	    if (esito.isWarning()) {
		return true;
	    }
	}
	return false;
    }
}
