package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.sottoscrittori;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProntaAllInvio;

@Service
public class SottoscrittoreEventoComunicazioneProntaAllInvioServiceImpl
	extends SottoscrittoreEventoBollettazioneBase<EventoComunicazioneProntaAllInvio> {

    @Override
    void onEventInternal(EventoComunicazioneProntaAllInvio e) {

	// Non fa nulla, lo step dovrebbe provvedere ad inviare la comunicazione
    }
}
