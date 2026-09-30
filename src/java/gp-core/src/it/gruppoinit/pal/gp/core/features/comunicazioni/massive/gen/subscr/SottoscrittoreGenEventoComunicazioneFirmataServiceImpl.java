package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneFirmata;

@Service
public class SottoscrittoreGenEventoComunicazioneFirmataServiceImpl extends SottoscrittoreEventoGenBase<EventoComunicazioneFirmata> {

    @Override
    void onEventInternal(EventoComunicazioneFirmata e) {

	// Uno o più documenti della comunicazione sono stati firmati. Per ora non fa nulla
    }
}
