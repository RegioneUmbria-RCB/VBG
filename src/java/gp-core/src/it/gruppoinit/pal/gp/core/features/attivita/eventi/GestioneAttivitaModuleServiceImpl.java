package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoAttivitaCreataServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoIstanzaCollegataServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoIstanzaModificaAzioneServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoIstanzaModificaDataValiditaServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoIstanzaModificaOperanteServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoIstanzaScollegataServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoOrdineIstanzaModificatoServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoSchedaDinamicaAggiuntaServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoSchedaDinamicaEliminataServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoSchedaDinamicaIstanzaEliminataServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoSchedaDinamicaIstanzaSalvataServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoSchedaDinamicaSalvataServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoSnapshotInseritoServiceImpl;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori.SottoscrittoreEventoSnapshotModificatoServiceImpl;
import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;

@Service
public class GestioneAttivitaModuleServiceImpl extends EventBusModule {

    @Autowired
    protected GestioneAttivitaModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoIstanzaCollegata.class, SottoscrittoreEventoIstanzaCollegataServiceImpl.class);
	eventPublisher.add(EventoIstanzaScollegata.class, SottoscrittoreEventoIstanzaScollegataServiceImpl.class);
	eventPublisher.add(EventoIstanzaModificaAzione.class, SottoscrittoreEventoIstanzaModificaAzioneServiceImpl.class);
	eventPublisher.add(EventoIstanzaModificaDataValidita.class, SottoscrittoreEventoIstanzaModificaDataValiditaServiceImpl.class);
	eventPublisher.add(EventoIstanzaModificaOperante.class, SottoscrittoreEventoIstanzaModificaOperanteServiceImpl.class);
	eventPublisher.add(EventoAttivitaCreata.class, SottoscrittoreEventoAttivitaCreataServiceImpl.class);
	eventPublisher.add(EventoSnapshotModificato.class, SottoscrittoreEventoSnapshotModificatoServiceImpl.class);
	eventPublisher.add(EventoSnapshotInserito.class, SottoscrittoreEventoSnapshotInseritoServiceImpl.class);
	eventPublisher.add(EventoSchedaDinamicaAggiunta.class, SottoscrittoreEventoSchedaDinamicaAggiuntaServiceImpl.class);
	eventPublisher.add(EventoSchedaDinamicaEliminata.class, SottoscrittoreEventoSchedaDinamicaEliminataServiceImpl.class);
	eventPublisher.add(EventoSchedaDinamicaIstanzaEliminata.class, SottoscrittoreEventoSchedaDinamicaIstanzaEliminataServiceImpl.class);
	eventPublisher.add(EventoSchedaDinamicaIstanzaSalvata.class, SottoscrittoreEventoSchedaDinamicaIstanzaSalvataServiceImpl.class);
	eventPublisher.add(EventoSchedaDinamicaSalvata.class, SottoscrittoreEventoSchedaDinamicaSalvataServiceImpl.class);
	eventPublisher.add(EventoOrdineIstanzaModificato.class, SottoscrittoreEventoOrdineIstanzaModificatoServiceImpl.class);
    }
}
