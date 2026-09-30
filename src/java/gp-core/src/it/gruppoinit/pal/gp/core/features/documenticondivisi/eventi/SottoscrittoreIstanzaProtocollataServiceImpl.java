package it.gruppoinit.pal.gp.core.features.documenticondivisi.eventi;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.IDocumentiCondivisiService;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.VerticalizzazioneCondivisioneDocumentale;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoIstanzaProtocollata;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class SottoscrittoreIstanzaProtocollataServiceImpl implements IEventSubscriber<EventoIstanzaProtocollata> {

    private IDocumentiCondivisiService documentiCondivisiService;
    VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public SottoscrittoreIstanzaProtocollataServiceImpl(IDocumentiCondivisiService documentiCondivisiService,
	    VerticalizzazioniService verticalizzazioniService) {

	this.documentiCondivisiService = documentiCondivisiService;
	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    public void onEvent(EventoIstanzaProtocollata e) {

	VerticalizzazioneCondivisioneDocumentale verticalizzazione = new VerticalizzazioneCondivisioneDocumentale(verticalizzazioniService);
	if (!verticalizzazione.isAttiva()) {
	    return;
	}
	Set<Integer> idDocumenti = e.getDocumenti();
	Integer codiceIstanza = e.getCodiceIstanza();
	this.documentiCondivisiService.condividiDocumentiIstanza(idDocumenti, codiceIstanza);
    }
}
