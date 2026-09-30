package it.gruppoinit.pal.gp.core.features.documenticondivisi.eventi;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.IDocumentiCondivisiService;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.VerticalizzazioneCondivisioneDocumentale;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoMovimentoProtocollato;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

@Service
public class SottoscrittoreMovimentoProtocollatoServiceImpl implements IEventSubscriber<EventoMovimentoProtocollato> {

    private IDocumentiCondivisiService documentiCondivisiService;
    VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public SottoscrittoreMovimentoProtocollatoServiceImpl(MovimentiService movimentiService, IDocumentiCondivisiService documentiCondivisiService,
	    VerticalizzazioniService verticalizzazioniService) {

	this.documentiCondivisiService = documentiCondivisiService;
	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    public void onEvent(EventoMovimentoProtocollato e) {

	VerticalizzazioneCondivisioneDocumentale verticalizzazione = new VerticalizzazioneCondivisioneDocumentale(verticalizzazioniService);
	if (!verticalizzazione.isAttiva()) {
	    return;
	}
	Set<Integer> idDocumenti = e.getDocumenti();
	Integer codiceMovimento = e.getCodiceMovimento();
	this.documentiCondivisiService.condividiDocumentiMovimento(idDocumenti, codiceMovimento);
    }
}