package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoSchedaDinamicaIstanzaSalvata implements IEvent {

    private Integer idIstanza;
    private Integer idSchedaDinamica;

    public EventoSchedaDinamicaIstanzaSalvata(Integer idIstanza, Integer idSchedaDinamica) {

	if (idIstanza == null) {
	    throw new IllegalArgumentException("Impossibile generare l'evento EventoSchedaDinamicaIstanzaSalvata senza passare idIstanza");
	}
	if (idSchedaDinamica == null) {
	    throw new IllegalArgumentException("Impossibile generare l'evento EventoSchedaDinamicaIstanzaSalvata senza passare idSchedaDinamica");
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
