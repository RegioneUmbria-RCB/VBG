package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoRichiestaSbloccoAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoRichiestaSbloccoAutorizzazioneServiceImpl implements IEventSubscriber<EventoRichiestaSbloccoAutorizzazione> {

    private AutorizzazioniService autorizzazioniService;
    private VerticalizzazioneWSAttiService verticalizzazioneWSAttiService;
    private DocumentiAutorizzazioneService documentiAutorizzazioneService;

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setVerticalizzazioneWSAttiService(VerticalizzazioneWSAttiService verticalizzazioneWSAttiService) {

	this.verticalizzazioneWSAttiService = verticalizzazioneWSAttiService;
    }

    @Autowired
    public void setDocumentiAutorizzazioneService(DocumentiAutorizzazioneService documentiAutorizzazioneService) {

	this.documentiAutorizzazioneService = documentiAutorizzazioneService;
    }

    @Override
    public void onEvent(EventoRichiestaSbloccoAutorizzazione e) {

	if (!this.verticalizzazioneWSAttiService.isAttiva()) {
	    return;
	}
	Autorizzazioni aut = this.autorizzazioniService.findById(new PkId(e.getIdAutorizzazione()));
	if (!"numerazioneDaWSAttiServiceImpl".equalsIgnoreCase(aut.getTipologiaregistro().getNumerazioneCustom())) {
	    return;
	}
	if (!this.documentiAutorizzazioneService.documentPrincipalePresente(e.getIdAutorizzazione())) {
	    return;
	}
	throw new RuntimeException("Non è possibile sbloccare l'autorizzazione in quanto è già stata trasmessa al gestionale degli atti");
    }
}
