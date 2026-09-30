package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoOccupanteModificato;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;

@Service
public class SottoscrittoreAbbonamentoEventoOccupanteModificatoServiceImpl implements IEventSubscriber<EventoOccupanteModificato> {

    private static Logger logger = LoggerFactory.getLogger(SottoscrittoreAbbonamentoEventoOccupanteModificatoServiceImpl.class);
    @Autowired
    private IAbbonamentoService abbonamentoService;

    @Override
    public void onEvent(EventoOccupanteModificato e) throws EventAbortedException {

	if (e.getEsito().isErrore()) {
	    throw new EventAbortedException("Sono presenti degli errori di validazione e non è possibile procedere", this.getClass().getSimpleName(),
		    e);
	}
	try {
	    abbonamentoService.gestisciModificaOccupanteAutorizzazione(e);
	} catch (BorsellinoException e1) {
	    logger.error("Si sono verificati degli errori e non è possibile la modifica dell'occupante a causa di " + e1.getMessage(), e1);
	    throw new EventAbortedException(
		    "Si sono verificati degli errori e non è possibile la modifica dell'occupante a causa di " + e1.getMessage(),
		    this.getClass().getSimpleName(), e);
	}
    }
}
