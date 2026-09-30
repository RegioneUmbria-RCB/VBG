package it.gruppoinit.pal.gp.core.features.manifestazioni.presenze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckModificaOccupante;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;

@Service
public class SottoscrittorePresenzeEventoCheckModificaOccupanteServiceImpl implements IEventSubscriber<EventoCheckModificaOccupante> {

    @Autowired
    private IVerificaModificaAutorizzazioniSuPresenzeService presenzeService;

    @Override
    public void onEvent(EventoCheckModificaOccupante e) throws EventAbortedException {

	EsitoElaborazioneEvento checkPosso = presenzeService.checkPossoModificareOccupante(e.getIdAutOConc(), e.getNuovoOccupante());
	if (checkPosso.isErroreOWarning()) {
	    throw new EventAbortedException(checkPosso, e);
	}
    }
}
