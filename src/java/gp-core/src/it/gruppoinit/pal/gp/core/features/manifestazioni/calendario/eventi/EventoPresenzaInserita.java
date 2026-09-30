package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoPresenzaInserita implements IEvent {

    private MercatipresenzeD presenza;

    public EventoPresenzaInserita(MercatipresenzeD presenza) {

	super();
	this.presenza = presenza;
    }

    public MercatipresenzeD getPresenza() {

	return presenza;
    }
}
