package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario;

import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoConcessionarioSegnatoPresente;

public interface SistemaPosizioniDebitorieConcessionariJobService {

    void rilanciaEventoNelService(EventoConcessionarioSegnatoPresente paramEventoConcessionarioSegnatoPresente) throws EventAbortedException;
}