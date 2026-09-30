package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoConcessionarioSegnatoPresente;

@Service
public class SistemaPosizioniDebitorieConcessionariJobServiceImpl implements SistemaPosizioniDebitorieConcessionariJobService {

    @Autowired
    private IEventPublisher eventPublisher;

    public void rilanciaEventoNelService(EventoConcessionarioSegnatoPresente evt) throws EventAbortedException {

	this.eventPublisher.publishThrowOnFailure((IEvent) evt);
    }
}
