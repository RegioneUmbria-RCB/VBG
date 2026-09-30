package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoPagamentoStornato implements IEvent {

    private Integer idMercatiPresenzeD;

    public EventoPagamentoStornato(Integer idMercatiPresenzeD) {

	super();
	this.idMercatiPresenzeD = idMercatiPresenzeD;
    }

    public Integer getIdMercatiPresenzeD() {

	return idMercatiPresenzeD;
    }
}
