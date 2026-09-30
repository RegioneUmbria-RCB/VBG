package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoRchiestaAnnullamentoPagamento implements IEvent {

    private MercatipresenzeD presenza;

    public EventoRchiestaAnnullamentoPagamento(MercatipresenzeD presenza) {

	super();
	this.presenza = presenza;
    }

    public MercatipresenzeD getPresenza() {

	return presenza;
    }
}
