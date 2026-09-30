package it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniHelper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;

public class EsitoElaborazioneSubentri implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2385191973830140059L;

    enum ESITO {
	WARNING,
	ERROR
    }

    public boolean isWarning() {

	for (Entry<Integer, DatiElaborazioneSubentroAutorizzazione> entry : mappaEsitiAutorizzazione.entrySet()) {
	    DatiElaborazioneSubentroAutorizzazione dati = entry.getValue();
	    if (dati.isWarning()) {
		return true;
	    }
	}
	return false;
    }

    public boolean isErrore() {

	for (Entry<Integer, DatiElaborazioneSubentroAutorizzazione> entry : mappaEsitiAutorizzazione.entrySet()) {
	    DatiElaborazioneSubentroAutorizzazione dati = entry.getValue();
	    if (dati.isErrore()) {
		return true;
	    }
	}
	return false;
    }

    public boolean isErroreOWarning() {

	for (Entry<Integer, DatiElaborazioneSubentroAutorizzazione> entry : mappaEsitiAutorizzazione.entrySet()) {
	    DatiElaborazioneSubentroAutorizzazione dati = entry.getValue();
	    if (dati.isErroreOWarning()) {
		return true;
	    }
	}
	return false;
    }

    public EsitoElaborazioneSubentri() {

	this.mappaEsitiAutorizzazione = new HashMap<Integer, DatiElaborazioneSubentroAutorizzazione>();
    }

    private Map<Integer, DatiElaborazioneSubentroAutorizzazione> mappaEsitiAutorizzazione = null;

    public Map<Integer, DatiElaborazioneSubentroAutorizzazione> getMappaEsitiAutorizzazione() {

	return mappaEsitiAutorizzazione;
    }

    public void addEsito(AutorizzazioniHelper aut, EsitoElaborazioneEvento esito, AutorizzazioniService autorizzazioniService,
	    AutorizzazioniConcessioniService autorizzazioniConcessioniService) {

	Integer idAutorizzazione = aut.getAutorizzazione().getId().getCodice();
	DatiElaborazioneSubentroAutorizzazione es = mappaEsitiAutorizzazione.get(idAutorizzazione);
	if (es == null) {
	    Autorizzazioni autorizzazione = autorizzazioniService.findById(new PkId(idAutorizzazione));
	    AutorizzazioniConcessioni concessione = null;
	    if (aut.getConcessione() != null && aut.getConcessione().getId() != null && aut.getConcessione().getId().getCodice() != null) {
		concessione = autorizzazioniConcessioniService.findById(new PkId(aut.getConcessione().getId().getCodice()));
	    }
	    es = new DatiElaborazioneSubentroAutorizzazione(autorizzazione, concessione);
	}
	es.getEsiti().add(esito);
	mappaEsitiAutorizzazione.put(idAutorizzazione, es);
    }
}
