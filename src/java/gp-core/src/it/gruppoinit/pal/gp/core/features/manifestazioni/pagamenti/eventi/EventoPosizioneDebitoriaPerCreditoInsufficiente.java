package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoPosizioneDebitoriaPerCreditoInsufficiente implements IEvent {

    private Integer idMercatiPresenzeD;

    public EventoPosizioneDebitoriaPerCreditoInsufficiente(Integer idMercatiPresenzeD) {

	super();
	this.idMercatiPresenzeD = idMercatiPresenzeD;
    }

    public Integer getIdMercatiPresenzeD() {

	return idMercatiPresenzeD;
    }
}
