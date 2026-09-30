package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaModificaDataValidita;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ICalcoloSnapshotService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.ParametriCalcoloSnapshot;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

@Service
public class SottoscrittoreEventoIstanzaModificaDataValiditaServiceImpl implements IEventSubscriber<EventoIstanzaModificaDataValidita> {

    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private ICalcoloSnapshotService calcoloSnapshotService;

    @Override
    public void onEvent(EventoIstanzaModificaDataValidita e) {

	Istanze istanza = this.istanzeService.findById(new PkId(e.getCodiceIstanza()));
	if (istanza.getAttivita() != null) {
	    Date dataPrecedente = e.getDataValiditaVecchia() == null ? e.getDataValiditaNuova() : e.getDataValiditaVecchia();
	    Date dataSuccessiva = e.getDataValiditaNuova() == null ? e.getDataValiditaVecchia() : e.getDataValiditaNuova();
	    this.calcoloSnapshotService
		    .calcola(new ParametriCalcoloSnapshot(istanza.getAttivita().getId().getCodice(), dataPrecedente, dataSuccessiva));
	}
    }
}
