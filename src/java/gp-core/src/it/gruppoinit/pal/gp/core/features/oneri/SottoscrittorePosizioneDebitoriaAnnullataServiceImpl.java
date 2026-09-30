package it.gruppoinit.pal.gp.core.features.oneri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoPosizioneDebitoriaAnnullata;

@Service
public class SottoscrittorePosizioneDebitoriaAnnullataServiceImpl implements IEventSubscriber<EventoPosizioneDebitoriaAnnullata> {

    IOneriPosizioniDebitorieService oneriPosizioniDebitorieService;

    @Autowired
    public SottoscrittorePosizioneDebitoriaAnnullataServiceImpl(IOneriPosizioniDebitorieService istanzeoneriService) {

	this.oneriPosizioniDebitorieService = istanzeoneriService;
    }

    @Override
    public void onEvent(EventoPosizioneDebitoriaAnnullata e) {

	if (this.oneriPosizioniDebitorieService.posizioneDebitoriaAppartieneAOnere(e.getIdDettaglioPosizioneDebitoria())) {
	    this.oneriPosizioniDebitorieService.impostaNoteOnerePerPosizioneDebitoriaAnnullata(e.getIdDettaglioPosizioneDebitoria(),
		    e.getNoteAnnullamento());
	}
    }
}
