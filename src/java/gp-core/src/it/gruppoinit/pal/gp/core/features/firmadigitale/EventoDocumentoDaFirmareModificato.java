package it.gruppoinit.pal.gp.core.features.firmadigitale;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoDocumentoDaFirmareModificato implements IEvent {

    private int idDocumentoDaFirmare;

    public int getIdDocumentoDaFirmare() {

	return idDocumentoDaFirmare;
    }

    public EventoDocumentoDaFirmareModificato(int idDocumentoDaFirmare) {

	this.idDocumentoDaFirmare = idDocumentoDaFirmare;
    }
}
