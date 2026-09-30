package it.gruppoinit.pal.gp.core.features.manifestazioni.presenze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneConcessioneEliminata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckModificaDataCessazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckModificaOccupante;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckSubentro;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoDataCessazioneSubentroModificata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoSubentroEffettuato;
import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;

@Service
public class GestionePresenzeEventModuleServiceImpl extends EventBusModule {

    @Autowired
    protected GestionePresenzeEventModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoCheckSubentro.class, SottoscrittorePresenzeEventoCheckSubentroServiceImpl.class);
	eventPublisher.add(EventoSubentroEffettuato.class, SottoscrittorePresenzeSubentroEffettuatoServiceImpl.class);
	eventPublisher.add(EventoCheckModificaOccupante.class, SottoscrittorePresenzeEventoCheckModificaOccupanteServiceImpl.class);
	eventPublisher.add(EventoCheckModificaDataCessazione.class, SottoscrittorePresenzeEventoCheckModificaDataCessazioneServiceImpl.class);
	eventPublisher.add(EventoDataCessazioneSubentroModificata.class,
		SottoscrittorePresenzeEventoDataCessazioneSubentroModificataServiceImpl.class);
	eventPublisher.add(EventoAutorizzazioneConcessioneEliminata.class, SottoscrittoreEventoAutorizzazioneConcessioneEliminataServiceImpl.class);
    }
}
