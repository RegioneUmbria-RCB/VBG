package it.gruppoinit.pal.gp.core.features.oneri.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoPagamentoOnereRegistrato implements IEvent {

    private Integer idIstanzeOneri;

    public EventoPagamentoOnereRegistrato(Integer idIstanzeOneri) {

	if (idIstanzeOneri == null) {
	    throw new IllegalArgumentException("L'id dell'onere non può essere nullo");
	}
	if (idIstanzeOneri <= 0) {
	    throw new IllegalArgumentException("L'id dell'onere non può essere minore di 0");
	}
	this.idIstanzeOneri = idIstanzeOneri;
    }

    public Integer getIdIstanzeOneri() {

	return idIstanzeOneri;
    }

    @Override
    public String toString() {

	return "[idIstanzeOneri: " + idIstanzeOneri + "]";
    }
}
