package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaScollegata;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ICalcoloSnapshotService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.ParametriCalcoloSnapshot;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoIstanzaScollegataServiceImpl implements IEventSubscriber<EventoIstanzaScollegata> {

    @Autowired
    private ICalcoloSnapshotService calcoloSnapshotService;

    @Override
    public void onEvent(EventoIstanzaScollegata e) {

	if (e.getDataElaborazione() != null) {
	    this.calcoloSnapshotService.calcola(new ParametriCalcoloSnapshot(e.getIdAttivita(), e.getDataElaborazione(), e.getDataElaborazione()));
	} else {
	    this.calcoloSnapshotService.ricalcola(e.getIdAttivita());
	}
    }
}
