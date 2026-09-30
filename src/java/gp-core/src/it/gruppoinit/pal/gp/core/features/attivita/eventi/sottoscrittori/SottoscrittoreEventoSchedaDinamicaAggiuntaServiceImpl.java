package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.datidinamici.IDatiDinamiciService;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSchedaDinamicaAggiunta;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoSchedaDinamicaAggiuntaServiceImpl implements IEventSubscriber<EventoSchedaDinamicaAggiunta> {

    @Autowired
    private IDatiDinamiciService datiDinamiciService;

    @Override
    public void onEvent(EventoSchedaDinamicaAggiunta e) {

	this.datiDinamiciService.gestisciSchedaDinamicaAggiuntaAdAttivita(e.getIdAttivita(), e.getIdSchedaDinamica());
    }
}
