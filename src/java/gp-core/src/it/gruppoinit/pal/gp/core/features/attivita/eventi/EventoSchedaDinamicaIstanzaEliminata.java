package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoSchedaDinamicaIstanzaEliminata implements IEvent {

    private Integer idIstanza;
    private Integer idSchedaDinamica;

    public EventoSchedaDinamicaIstanzaEliminata(Integer idIstanza, Integer idSchedaDinamica) {

	if (idIstanza == null) {
	    throw new IllegalArgumentException("Impossibile generare l'evento EventoSchedaDinamicaIstanzaEliminata senza passare idIstanza");
	}
	if (idSchedaDinamica == null) {
	    throw new IllegalArgumentException("Impossibile generare l'evento EventoSchedaDinamicaIstanzaEliminata senza passare idSchedaDinamica");
	}
	this.idIstanza = idIstanza;
	this.idSchedaDinamica = idSchedaDinamica;
    }

    public Integer getIdIstanza() {

	return idIstanza;
    }

    public Integer getIdSchedaDinamica() {

	return idSchedaDinamica;
    }
}
