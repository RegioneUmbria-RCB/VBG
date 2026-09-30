package it.gruppoinit.pal.gp.core.features.manifestazioni.presenze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckModificaDataCessazione;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;

@Service
public class SottoscrittorePresenzeEventoCheckModificaDataCessazioneServiceImpl implements IEventSubscriber<EventoCheckModificaDataCessazione> {

    @Autowired
    private IVerificaModificaAutorizzazioniSuPresenzeService presenzeService;

    @Override
    public void onEvent(EventoCheckModificaDataCessazione e) throws EventAbortedException {

	EsitoElaborazioneEvento checkPosso = presenzeService.checkPossoModificareDataCessazioneSubentro(e.getIdSubentroDaModificare(),
		e.getNuovaDataCessazione());
	if (checkPosso.isErroreOWarning()) {
	    throw new EventAbortedException(checkPosso, e);
	}
    }
}
