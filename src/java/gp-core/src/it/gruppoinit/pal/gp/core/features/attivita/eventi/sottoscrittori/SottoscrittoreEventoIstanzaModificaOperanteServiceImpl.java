package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaModificaOperante;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ICalcoloSnapshotService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.ParametriCalcoloSnapshot;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

@Service
public class SottoscrittoreEventoIstanzaModificaOperanteServiceImpl implements IEventSubscriber<EventoIstanzaModificaOperante> {

    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private ICalcoloSnapshotService calcoloSnapshotService;

    @Override
    public void onEvent(EventoIstanzaModificaOperante e) {

	Istanze istanza = this.istanzeService.findById(new PkId(e.getCodiceIstanza()));
	if (istanza.getAttivita() != null) {
	    if (istanza.getDatavalidita() != null) {
		this.calcoloSnapshotService.calcola(new ParametriCalcoloSnapshot(istanza.getAttivita().getId().getCodice(), istanza.getDatavalidita(),
			istanza.getDatavalidita()));
	    } else {
		this.calcoloSnapshotService.ricalcola(istanza.getAttivita().getId().getCodice());
	    }
	}
    }
}