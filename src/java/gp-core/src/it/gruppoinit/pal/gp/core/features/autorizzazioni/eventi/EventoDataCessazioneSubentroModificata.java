package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoDataCessazioneSubentroModificata implements IEvent {

    private Integer idAutorizzazioniSubentri;
    private Date vecchiaDataCessazione;
    private EsitoModificaDataCessazione esito;

    public EventoDataCessazioneSubentroModificata(Integer idAutorizzazioniSubentri, Date vecchiaDataCessazione, EsitoModificaDataCessazione esito) {

	super();
	this.idAutorizzazioniSubentri = idAutorizzazioniSubentri;
	this.vecchiaDataCessazione = vecchiaDataCessazione;
	this.esito = esito;
    }

    public Integer getIdAutorizzazioniSubentri() {

	return idAutorizzazioniSubentri;
    }

    public Date getVecchiaDataCessazione() {

	return vecchiaDataCessazione;
    }

    public EsitoModificaDataCessazione getEsito() {

	return esito;
    }
}
