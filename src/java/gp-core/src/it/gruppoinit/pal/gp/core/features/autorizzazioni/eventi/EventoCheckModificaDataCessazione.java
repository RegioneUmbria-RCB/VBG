package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoCheckModificaDataCessazione implements IEvent {

    private Integer idSubentroDaModificare;
    private Date nuovaDataCessazione;

    public EventoCheckModificaDataCessazione(Integer idSubentroDaModificare, Date nuovaDataCessazione) {

	super();
	this.idSubentroDaModificare = idSubentroDaModificare;
	this.nuovaDataCessazione = nuovaDataCessazione;
    }

    public Integer getIdSubentroDaModificare() {

	return idSubentroDaModificare;
    }

    public Date getNuovaDataCessazione() {

	return nuovaDataCessazione;
    }
}
