package it.gruppoinit.pal.gp.core.features.manifestazioni.presenze;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoSubentroEffettuato;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniSubentriException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.DatiElaborazioneSubentroAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.EsitoElaborazioneSubentri;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean.CHIAMANTE;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class SottoscrittorePresenzeSubentroEffettuatoServiceImpl implements IEventSubscriber<EventoSubentroEffettuato> {

    @Autowired
    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    @Autowired
    private IVerificaModificaAutorizzazioniSuPresenzeService verificaSubentriSuPresenzeService;

    @Override
    public void onEvent(EventoSubentroEffettuato e) throws EventAbortedException {

	AutorizzazioniSubentri sub = autorizzazioniSubentriService.findById(new PkId(e.getIdSubentroEffettuato()));
	Integer idAutorizzazioni = sub.getAutorizzazioni().getId().getCodice();
	EsitoElaborazioneSubentri esitoElaborazioneSubentri = e.getEsitoElaborazioneSubentri();
	DatiElaborazioneSubentroAutorizzazione esitiAut = null;
	if (esitoElaborazioneSubentri != null && (esitiAut = esitoElaborazioneSubentri.getMappaEsitiAutorizzazione().get(idAutorizzazioni)) != null) {
	    if (esitiAut.isErrore()) {
		throw new EventAbortedException("Sono presenti degli errori bloccanti e non è possibile effettuare il subentro",
			this.getClass().getSimpleName(), e);
	    }
	    if (esitiAut.isWarning()) {
		for (EsitoElaborazioneEvento esito : esitiAut.getEsiti()) {
		    elaboraWarningsPerAutorizzazione(esito.getWarnings(), e);
		}
	    }
	}
    }

    private void elaboraWarningsPerAutorizzazione(List<OperazioneEventoBean> warnings, EventoSubentroEffettuato e) throws EventAbortedException {

	for (OperazioneEventoBean w : warnings) {
	    CHIAMANTE c = w.getChiamante();
	    if (c.equals(CHIAMANTE.MERCATIPRESENZE_D)) {
		// idRiferimento = idPresenza
		if (!Utilities.isInteger(w.getIdRiferimento())) {
		    throw new EventAbortedException("Identificativo Riferimento non numerico " + w.getIdRiferimento(),
			    this.getClass().getSimpleName(), e);
		}
		try {
		    verificaSubentriSuPresenzeService.effettuaSubentroSuPresenza(Integer.parseInt(w.getIdRiferimento().trim()),
			    e.getIdSubentroEffettuato());
		} catch (OperazioniSubentriException e1) {
		    throw new EventAbortedException(e1, this.getClass().getSimpleName(), e);
		}
	    }
	}
    }
}
