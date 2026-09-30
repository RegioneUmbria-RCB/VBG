package it.gruppoinit.pal.gp.core.features.oneri.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoPreOnereIstanzaEliminato implements IEvent {

    private int istanzeOneriId;

    public EventoPreOnereIstanzaEliminato(int istanzeOneriId) {

	this.istanzeOneriId = istanzeOneriId;
    }

    public int getIstanzeOneriId() {

	return istanzeOneriId;
    }
}
