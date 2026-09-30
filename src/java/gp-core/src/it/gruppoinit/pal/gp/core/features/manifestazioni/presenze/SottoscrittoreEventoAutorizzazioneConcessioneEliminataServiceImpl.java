package it.gruppoinit.pal.gp.core.features.manifestazioni.presenze;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoCancellazioneAutOConc;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneConcessioneEliminata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioneCancellazioneAutConcException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniSubentriException;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean.CHIAMANTE;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class SottoscrittoreEventoAutorizzazioneConcessioneEliminataServiceImpl implements IEventSubscriber<EventoAutorizzazioneConcessioneEliminata> {

    @Autowired
    private IVerificaModificaAutorizzazioniSuPresenzeService presenzeService;

    @Override
    public void onEvent(EventoAutorizzazioneConcessioneEliminata e) throws EventAbortedException {

	EsitoCancellazioneAutOConc esitoElaborazione = e.getEsito();
	if (esitoElaborazione != null) {
	    if (esitoElaborazione.isErrore()) {
		String messaggio = "Sono presenti degli errori bloccanti e non è possibile effettuare la cancellazione ";
		if (e.isConcessione()) {
		    messaggio += " della concessione ";
		} else {
		    messaggio += " dell'autorizzazione ";
		}
		messaggio += e.getEstremiAtto();
		throw new EventAbortedException(messaggio, this.getClass().getSimpleName(), e);
	    }
	    if (esitoElaborazione.isWarning()) {
		for (EsitoElaborazioneEvento esito : esitoElaborazione.getEsiti()) {
		    elaboraWarningsPerAutorizzazione(esito.getWarnings(), e);
		}
	    }
	}
    }

    private void elaboraWarningsPerAutorizzazione(List<OperazioneEventoBean> warnings, EventoAutorizzazioneConcessioneEliminata e)
	    throws EventAbortedException {

	for (OperazioneEventoBean w : warnings) {
	    CHIAMANTE c = w.getChiamante();
	    if (c.equals(CHIAMANTE.MERCATIPRESENZE_D)) {
		// idRiferimento = idPresenza
		if (!Utilities.isInteger(w.getIdRiferimento())) {
		    throw new EventAbortedException("Identificativo Riferimento non numerico " + w.getIdRiferimento(),
			    this.getClass().getSimpleName(), e);
		}
		try {
		    Integer idPresenza = Integer.parseInt(w.getIdRiferimento());
		    presenzeService.effettuaOperazioniCancellazioneAutConcSuPresenza(idPresenza, e.getIdAutorizzazioni());
		} catch (OperazioneCancellazioneAutConcException e1) {
		    throw new EventAbortedException(e1, this.getClass().getSimpleName(), e);
		}
	    }
	}
    }
}
