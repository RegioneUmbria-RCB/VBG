package it.gruppoinit.pal.gp.core.features.movimenti.istanzecollegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.eventi.EventoIstanzaScollegata;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;

@Service
public class MovimentiPraticaPadreScollegamentoSottoscrittoreServiceImpl implements IEventSubscriber<EventoIstanzaScollegata> {

    @Autowired
    private IstanzecollegateService istanzecollegateService;

    @Override
    public void onEvent(EventoIstanzaScollegata e) {

	try {
	    istanzecollegateService.deleteMovimentoInIstanzaCollegata(e.getCodiceIstanzaOrigine(), e.getCodiceIstanzaDestinazione());
	} catch (OperazioniAutomaticheException e1) {
	    // è già loggato nel service
	}
    }
}
