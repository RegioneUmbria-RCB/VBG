package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.datidinamici.IDatiDinamiciService;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSnapshotInserito;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoSnapshotInseritoServiceImpl implements IEventSubscriber<EventoSnapshotInserito> {

    @Autowired
    private IDatiDinamiciService datiDinamiciService;

    @Override
    public void onEvent(EventoSnapshotInserito e) {

	this.datiDinamiciService.gestisciNuovoSnapshot(e.getIdAttivita(), e.getDataRiferimento());
    }
}
