package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoSchedaDinamicaEliminata implements IEvent {

    private Integer idAttivita;
    private Integer idSchedaDinamica;
    private List<Integer> idCampiDinamiciDaEliminare;

    public EventoSchedaDinamicaEliminata(Integer idAttivita, Integer idSchedaDinamica, List<Integer> idCampiDinamiciDaEliminare) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile generare l'evento EventoSchedaDinamicaEliminata senza passare idAttivita");
	}
	if (idSchedaDinamica == null) {
	    throw new IllegalArgumentException("Impossibile generare l'evento EventoSchedaDinamicaEliminata senza passare idSchedaDinamica");
	}
	if (idCampiDinamiciDaEliminare == null) {
	    throw new IllegalArgumentException(
		    "Impossibile generare l'evento EventoSchedaDinamicaEliminata senza passare la lista dei campi da eliminare");
	}
	this.idAttivita = idAttivita;
	this.idSchedaDinamica = idSchedaDinamica;
	this.idCampiDinamiciDaEliminare = idCampiDinamiciDaEliminare;
    }

    public Integer getIdAttivita() {

	return idAttivita;
    }

    public Integer getIdSchedaDinamica() {

	return idSchedaDinamica;
    }

    public List<Integer> getIdCampiDinamiciDaEliminare() {

	return idCampiDinamiciDaEliminare;
    }
}
