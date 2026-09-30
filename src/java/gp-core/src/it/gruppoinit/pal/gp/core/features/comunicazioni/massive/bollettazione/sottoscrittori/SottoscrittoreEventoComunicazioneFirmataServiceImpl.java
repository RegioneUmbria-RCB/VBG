package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.sottoscrittori;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneFirmata;

@Service
public class SottoscrittoreEventoComunicazioneFirmataServiceImpl extends SottoscrittoreEventoBollettazioneBase<EventoComunicazioneFirmata> {

    @Override
    void onEventInternal(EventoComunicazioneFirmata e) {

	// Uno o più documenti della comunicazione sono stati firmati. Per ora non fa nulla
    }
}
