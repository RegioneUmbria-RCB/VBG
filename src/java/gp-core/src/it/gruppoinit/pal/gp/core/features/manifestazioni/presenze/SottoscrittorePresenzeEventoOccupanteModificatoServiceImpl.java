package it.gruppoinit.pal.gp.core.features.manifestazioni.presenze;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoOccupanteModificato;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniSubentriException;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean.CHIAMANTE;

@Service
public class SottoscrittorePresenzeEventoOccupanteModificatoServiceImpl implements IEventSubscriber<EventoOccupanteModificato> {

    @Autowired
    private IVerificaModificaAutorizzazioniSuPresenzeService verificaModificaAutorizzazioniSuPresenzeService;

    @Override
    public void onEvent(EventoOccupanteModificato e) throws EventAbortedException {

	if (e.getEsito().isErrore()) {
	    throw new EventAbortedException("Sono presenti degli errori bloccanti e non è possibile effettuare l'operazione",
		    this.getClass().getSimpleName(), e);
	}
	List<EsitoElaborazioneEvento> esiti = e.getEsito().getEsiti();
	for (EsitoElaborazioneEvento esito : esiti) {
	    for (OperazioneEventoBean warning : esito.getWarnings()) {
		if (warning.getChiamante().equals(CHIAMANTE.MERCATIPRESENZE_D)) {
		    try {
			verificaModificaAutorizzazioniSuPresenzeService
				.effettuaModificaOccupanteSuPresenza(Integer.parseInt(warning.getIdRiferimento().trim()), e.getIdAuOConc());
		    } catch (OperazioniSubentriException e1) {
			throw new EventAbortedException(e1, this.getClass().getSimpleName(), e);
		    }
		}
	    }
	}
    }
}
