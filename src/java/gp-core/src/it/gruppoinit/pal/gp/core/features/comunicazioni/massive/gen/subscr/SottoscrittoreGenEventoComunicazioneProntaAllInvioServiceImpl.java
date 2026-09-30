package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProntaAllInvio;

@Service
public class SottoscrittoreGenEventoComunicazioneProntaAllInvioServiceImpl
	extends SottoscrittoreEventoGenBase<EventoComunicazioneProntaAllInvio> {

    @Override
    void onEventInternal(EventoComunicazioneProntaAllInvio e) {

	// Non fa nulla, lo step dovrebbe provvedere ad inviare la comunicazione
    }
}
