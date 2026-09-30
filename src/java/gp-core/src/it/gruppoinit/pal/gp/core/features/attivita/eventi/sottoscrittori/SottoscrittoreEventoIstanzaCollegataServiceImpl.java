package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaCollegata;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ICalcoloSnapshotService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.ParametriCalcoloSnapshot;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

@Service
public class SottoscrittoreEventoIstanzaCollegataServiceImpl implements IEventSubscriber<EventoIstanzaCollegata> {

    @Autowired
    private ICalcoloSnapshotService calcoloSnapshotService;
    @Autowired
    private IstanzeService istanzeService;
    private Logger log = Logger.getLogger(SottoscrittoreEventoIstanzaCollegataServiceImpl.class);

    @Override
    public void onEvent(EventoIstanzaCollegata e) {

	ricalcolaOrdineAttivitaBatch(e.getIdAttivita(), e.getIstanza());
	if (e.getDataElaborazione() != null) {
	    this.calcoloSnapshotService.calcola(new ParametriCalcoloSnapshot(e.getIdAttivita(), e.getDataElaborazione(), e.getDataElaborazione()));
	} else {
	    this.calcoloSnapshotService.ricalcola(e.getIdAttivita());
	}
    }

    private void ricalcolaOrdineAttivitaBatch(Integer idAttivita, Istanze nuovaIstanza) {

	List<Istanze> istanze = istanzeService.findByAttivita(idAttivita);
	Date dataValiditaNuova = nuovaIstanza.getDatavalidita();
	if (dataValiditaNuova == null) {
	    log.debug("la nuova istanza " + nuovaIstanza.toString() + " non ha datavalidita -> salto il riordinamento");
	    return;
	}
	log.debug("istanza collegata: " + nuovaIstanza.toString());
	// 1. filtriamo istanze con la stessa datavalidita dell'istanza appena collegata
	List<Istanze> daRiordinare = new ArrayList<Istanze>();
	for (Istanze i : istanze) {
	    if (i.getDatavalidita() == null) {
		log.debug("l'istanza " + i.toString() + " non ha datavalidita");
	    } else {
		if (dataValiditaNuova.equals(i.getDatavalidita())) {
		    log.debug("l'istanza " + i.toString() + " HA datavalidita uguale, per cui LA AGGIUNGO tra quelle da ordinare");
		    daRiordinare.add(i);
		} else {
		    log.debug("l'istanza " + i.toString() + " non ha datavalidita uguale, per cui non la aggiungo tra quelle da ordinare");
		}
	    }
	}
	// 2. ordinamento per codice DESC
	Collections.sort(daRiordinare, new Comparator<Istanze>() {

	    public int compare(Istanze a, Istanze b) {

		return b.getId().getCodice().compareTo(a.getId().getCodice());
	    }
	});
	// 3. riassegnazione ordine
	int ordine = 0;
	for (Istanze istanza : daRiordinare) {
	    log.debug("ordine " + ordine + " assegnato a istanza con codice " + istanza.getId().getCodice());
	    istanza.setAttivitaOrdine(ordine++);
	    istanzeService.update(istanza);
	}
    }
}
