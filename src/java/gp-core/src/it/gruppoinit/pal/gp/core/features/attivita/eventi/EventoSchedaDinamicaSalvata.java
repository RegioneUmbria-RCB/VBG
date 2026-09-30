package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoSchedaDinamicaSalvata implements IEvent {

    private Integer idAttivita;
    private Integer idSchedaDinamica;

    public EventoSchedaDinamicaSalvata(Integer idAttivita, Integer idSchedaDinamica) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile generare l'evento EventoSchedaDinamicaSalvata senza passare idAttivita");
	}
	if (idSchedaDinamica == null) {
	    throw new IllegalArgumentException("Impossibile generare l'evento EventoSchedaDinamicaSalvata senza passare idSchedaDinamica");
	}
	this.idAttivita = idAttivita;
	this.idSchedaDinamica = idSchedaDinamica;
    }

    public Integer getIdAttivita() {

	return idAttivita;
    }

    public Integer getIdSchedaDinamica() {

	return idSchedaDinamica;
    }
}
