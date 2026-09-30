package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.CheckSubentroRequest;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoCheckSubentro implements IEvent {

    private CheckSubentroRequest request;

    public EventoCheckSubentro(CheckSubentroRequest request) {

	this.request = request;
    }

    public CheckSubentroRequest getRequest() {

	return request;
    }
}
