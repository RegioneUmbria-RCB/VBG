package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.attivita.datidinamici.IDatiDinamiciService;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSchedaDinamicaIstanzaSalvata;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

@Service
public class SottoscrittoreEventoSchedaDinamicaIstanzaSalvataServiceImpl implements IEventSubscriber<EventoSchedaDinamicaIstanzaSalvata> {

    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IDatiDinamiciService datiDinamiciService;

    @Override
    public void onEvent(EventoSchedaDinamicaIstanzaSalvata e) {

	Istanze istanza = this.istanzeService.findById(new PkId(e.getIdIstanza()));
	if (istanza.getAttivita() == null || istanza.getDatavalidita() == null) {
	    return;
	}
	this.datiDinamiciService.gestisciSchedaDinamicaIstanzaSalvata(istanza.getAttivita().getId().getCodice(), e.getIdIstanza(),
		e.getIdSchedaDinamica(), istanza.getDatavalidita());
    }
}
