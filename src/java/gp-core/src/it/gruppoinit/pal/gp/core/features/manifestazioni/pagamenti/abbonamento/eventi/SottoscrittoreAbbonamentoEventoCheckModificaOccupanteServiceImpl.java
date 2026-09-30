package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckModificaOccupante;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;

@Service
public class SottoscrittoreAbbonamentoEventoCheckModificaOccupanteServiceImpl implements IEventSubscriber<EventoCheckModificaOccupante> {

    @Autowired
    private IAbbonamentoService abbonamentoService;

    @Override
    public void onEvent(EventoCheckModificaOccupante e) throws EventAbortedException {

	EsitoElaborazioneEvento checkPosso = abbonamentoService.checkPossoModificareOccupante(e.getIdAutOConc(), e.getNuovoOccupante());
	if (checkPosso.isErroreOWarning()) {
	    throw new EventAbortedException(checkPosso, e);
	}
    }
}
