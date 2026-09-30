package it.gruppoinit.pal.gp.core.features.manifestazioni.presenze;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoModificaDataCessazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoDataCessazioneSubentroModificata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniSubentriException;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean.CHIAMANTE;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class SottoscrittorePresenzeEventoDataCessazioneSubentroModificataServiceImpl
	implements IEventSubscriber<EventoDataCessazioneSubentroModificata> {

    @Autowired
    private IVerificaModificaAutorizzazioniSuPresenzeService presenzeService;

    @Override
    public void onEvent(EventoDataCessazioneSubentroModificata e) throws EventAbortedException {

	EsitoModificaDataCessazione esitoElaborazioneSubentri = e.getEsito();
	if (esitoElaborazioneSubentri != null) {
	    if (esitoElaborazioneSubentri.isErrore()) {
		throw new EventAbortedException(
			"Sono presenti degli errori bloccanti e non è possibile effettuare l'operazione di modifica data cessazione",
			this.getClass().getSimpleName(), e);
	    }
	    if (esitoElaborazioneSubentri.isWarning()) {
		for (EsitoElaborazioneEvento esito : esitoElaborazioneSubentri.getEsiti()) {
		    elaboraWarningsPerAutorizzazione(esito.getWarnings(), e);
		}
	    }
	}
    }

    private void elaboraWarningsPerAutorizzazione(List<OperazioneEventoBean> warnings, EventoDataCessazioneSubentroModificata e)
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
		    presenzeService.effettuaModificaDataCessazioneSubentroSuPresenza(idPresenza, e.getIdAutorizzazioniSubentri(),
			    e.getVecchiaDataCessazione());
		} catch (OperazioniSubentriException e1) {
		    throw new EventAbortedException(e1, this.getClass().getSimpleName(), e);
		}
	    }
	}
    }
}
