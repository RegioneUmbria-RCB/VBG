package it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckSubentro;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;

@Service
public class SottoscrittoreSubentriEventoCheckSubentroServiceImpl implements IEventSubscriber<EventoCheckSubentro> {

    @Autowired
    private IVerificaCheckSubentriService checkService;

    @Override
    public void onEvent(EventoCheckSubentro e) throws EventAbortedException {

	EsitoElaborazioneEvento checkPossoSubentrare = checkService.checkPossoSubentrare(e.getRequest());
	if (checkPossoSubentrare.isErroreOWarning()) {
	    throw new EventAbortedException(checkPossoSubentrare, e);
	}
    }
}
