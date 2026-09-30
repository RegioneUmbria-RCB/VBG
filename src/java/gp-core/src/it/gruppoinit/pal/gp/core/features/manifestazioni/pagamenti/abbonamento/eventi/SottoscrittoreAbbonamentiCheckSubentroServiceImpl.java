package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoSubentroEffettuato;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;

@Service
public class SottoscrittoreAbbonamentiCheckSubentroServiceImpl implements IEventSubscriber<EventoSubentroEffettuato> {

    private static Logger logger = LoggerFactory.getLogger(SottoscrittoreAbbonamentiCheckSubentroServiceImpl.class);
    @Autowired
    private IAbbonamentoService abbonamentoService;

    @Override
    public void onEvent(EventoSubentroEffettuato e) throws EventAbortedException {

	try {
	    abbonamentoService.gestisciSubentroAutorizzazione(e.getIdSubentroEffettuato());
	} catch (BorsellinoException e1) {
	    logger.error("Sono presenti degli errori bloccanti e non è possibile effettuare il subentro " + e1.getMessage(), e1);
	    throw new EventAbortedException("Sono presenti degli errori bloccanti e non è possibile effettuare il subentro " + e1.getMessage(),
		    this.getClass().getSimpleName(), e);
	}
    }
}
