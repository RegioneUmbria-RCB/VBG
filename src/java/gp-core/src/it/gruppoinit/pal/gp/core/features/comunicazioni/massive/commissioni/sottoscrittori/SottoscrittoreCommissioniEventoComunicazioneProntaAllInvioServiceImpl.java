package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.sottoscrittori;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProntaAllInvio;

@Service
public class SottoscrittoreCommissioniEventoComunicazioneProntaAllInvioServiceImpl
	extends SottoscrittoreEventoCommissioniBase<EventoComunicazioneProntaAllInvio> {

    @Override
    void onEventInternal(EventoComunicazioneProntaAllInvio e) {

	// Non fa nulla, lo step dovrebbe provvedere ad inviare la comunicazione
    }
}
