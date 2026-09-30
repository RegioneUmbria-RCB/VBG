package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.datidinamici.IDatiDinamiciService;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSchedaDinamicaEliminata;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoSchedaDinamicaEliminataServiceImpl implements IEventSubscriber<EventoSchedaDinamicaEliminata> {

    @Autowired
    private IDatiDinamiciService datiDinamiciService;

    @Override
    public void onEvent(EventoSchedaDinamicaEliminata e) {

	this.datiDinamiciService.gestisciSchedaDinamicaAttivitaEliminata(e.getIdAttivita(), e.getIdSchedaDinamica(),
		e.getIdCampiDinamiciDaEliminare());
    }
}
