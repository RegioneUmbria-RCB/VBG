package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.datidinamici.IDatiDinamiciService;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSnapshotModificato;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoSnapshotModificatoServiceImpl implements IEventSubscriber<EventoSnapshotModificato> {

    @Autowired
    private IDatiDinamiciService datiDinamiciService;

    @Override
    public void onEvent(EventoSnapshotModificato e) {

	//Non è sufficiente capire se è cambiata la cardinalità, potrebbe rimanere la stessa cardinalità ma scollegata un'istanza
	//che modificha quindi la struttura dei dati dinamici e vanno ricalcolati
	//if (e.isSnapshotCambiaCardinalita()) {
	this.datiDinamiciService.gestisciRicalcoloDatiDinamici(e.getIdAttivita(), e.getDataRiferimento());
	//}
    }
}
