package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProntaPerProtocollazione;

@Service
public class SottoscrittoreGenEventoComunicazioneProntaPerProtocollazioneServiceImpl
	extends SottoscrittoreEventoGenBase<EventoComunicazioneProntaPerProtocollazione> {

    @Override
    void onEventInternal(EventoComunicazioneProntaPerProtocollazione e) {

	// Non fa nulla, lo step dovrebbe provvedere alla protocollazione
    }
}
