package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.EsitoElaborazioneSubentri;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoSubentroEffettuato implements IEvent {

    private Integer idSubentroEffettuato;
    private AutorizzazioniService.ENUM_COPIA_ONERI copiaONERI;
    private EsitoElaborazioneSubentri esitoElaborazioneSubentri;

    public EventoSubentroEffettuato(Integer idSubentroEffettuato, AutorizzazioniService.ENUM_COPIA_ONERI copiaONERI,
	    EsitoElaborazioneSubentri esitoElaborazioneSubentri) {

	super();
	this.idSubentroEffettuato = idSubentroEffettuato;
	this.copiaONERI = copiaONERI;
	this.esitoElaborazioneSubentri = esitoElaborazioneSubentri;
    }

    public AutorizzazioniService.ENUM_COPIA_ONERI getCopiaONERI() {

	return copiaONERI;
    }

    public Integer getIdSubentroEffettuato() {

	return idSubentroEffettuato;
    }

    public EsitoElaborazioneSubentri getEsitoElaborazioneSubentri() {

	return esitoElaborazioneSubentri;
    }
}
