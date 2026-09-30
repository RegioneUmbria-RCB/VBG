package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoIstanzaModificaDataValidita implements IEvent {

    private Integer codiceIstanza;
    private Date dataValiditaVecchia;
    private Date dataValiditaNuova;

    public EventoIstanzaModificaDataValidita(Integer codiceIstanza, Date dataValiditaVecchia, Date dataValiditaNuova) {

	super();
	this.codiceIstanza = codiceIstanza;
	this.dataValiditaVecchia = dataValiditaVecchia;
	this.dataValiditaNuova = dataValiditaNuova;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public Date getDataValiditaVecchia() {

	return dataValiditaVecchia;
    }

    public Date getDataValiditaNuova() {

	return dataValiditaNuova;
    }
}
