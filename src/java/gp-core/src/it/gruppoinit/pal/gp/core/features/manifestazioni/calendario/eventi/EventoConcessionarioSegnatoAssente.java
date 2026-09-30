package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoConcessionarioSegnatoAssente implements IEvent {

    private Integer idMercatiPresenzeD;
    private Date dataPresenza;

    public EventoConcessionarioSegnatoAssente(Integer idMercatiPresenzeD, Date dataPresenza) {

	super();
	this.idMercatiPresenzeD = idMercatiPresenzeD;
	this.dataPresenza = dataPresenza;
    }

    public Integer getIdMercatiPresenzeD() {

	return idMercatiPresenzeD;
    }

    public Date getDataPresenza() {

	return dataPresenza;
    }
}
