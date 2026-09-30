package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.sottoscrittori;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProntaPerProtocollazione;

@Service
public class SottoscrittoreEventoComunicazioneProntaPerProtocollazioneServiceImpl
	extends SottoscrittoreEventoBollettazioneBase<EventoComunicazioneProntaPerProtocollazione> {

    @Override
    void onEventInternal(EventoComunicazioneProntaPerProtocollazione e) {

	// Non fa nulla, lo step dovrebbe provvedere alla protocollazione
    }
}
