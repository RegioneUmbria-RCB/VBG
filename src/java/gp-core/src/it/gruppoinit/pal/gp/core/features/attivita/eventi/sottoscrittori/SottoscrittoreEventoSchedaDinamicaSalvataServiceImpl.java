package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.datidinamici.IDatiDinamiciService;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSchedaDinamicaSalvata;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoSchedaDinamicaSalvataServiceImpl implements IEventSubscriber<EventoSchedaDinamicaSalvata> {

    @Autowired
    private IDatiDinamiciService datiDinamiciService;

    @Override
    public void onEvent(EventoSchedaDinamicaSalvata e) {

	this.datiDinamiciService.gestisciSchedaDinamicaAttivitaSalvata(e.getIdAttivita(), e.getIdSchedaDinamica());
    }
}
