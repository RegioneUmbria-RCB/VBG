package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoOrdineIstanzaModificato;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ICalcoloSnapshotService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.ParametriCalcoloSnapshot;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoOrdineIstanzaModificatoServiceImpl implements IEventSubscriber<EventoOrdineIstanzaModificato> {

    @Autowired
    private ICalcoloSnapshotService calcoloSnapshotService;

    @Override
    public void onEvent(EventoOrdineIstanzaModificato e) {

	if (e.getDataEvento() != null) {
	    this.calcoloSnapshotService.calcola(new ParametriCalcoloSnapshot(e.getIdAttivita(), e.getDataEvento(), e.getDataEvento()));
	} else {
	    this.calcoloSnapshotService.ricalcola(e.getIdAttivita());
	}
    }
}
