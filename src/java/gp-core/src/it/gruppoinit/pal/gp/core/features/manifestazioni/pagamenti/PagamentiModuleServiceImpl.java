package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoAssenzaGiustificata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoAssenzaGiustificataRevocata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoConcessionarioSegnatoPresente;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaInserita;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaRevocata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.EventoRchiestaAnnullamentoPagamento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.SottoscrittoreEventoAssenzaGiustificataRevocataServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.SottoscrittoreEventoAssenzaGiustificataServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.SottoscrittoreEventoConcessionarioSegnatoPresenteServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.SottoscrittoreEventoPresenzaInseritaServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.SottoscrittoreEventoPresenzaRevocataServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.SottoscrittoreEventoRchiestaAnnullamentoPagamentoServiceImpl;

@Service
public class PagamentiModuleServiceImpl extends EventBusModule {

    @Autowired
    public PagamentiModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoAssenzaGiustificataRevocata.class, SottoscrittoreEventoAssenzaGiustificataRevocataServiceImpl.class);
	eventPublisher.add(EventoAssenzaGiustificata.class, SottoscrittoreEventoAssenzaGiustificataServiceImpl.class);
	eventPublisher.add(EventoConcessionarioSegnatoPresente.class, SottoscrittoreEventoConcessionarioSegnatoPresenteServiceImpl.class);
	eventPublisher.add(EventoPresenzaInserita.class, SottoscrittoreEventoPresenzaInseritaServiceImpl.class);
	eventPublisher.add(EventoPresenzaRevocata.class, SottoscrittoreEventoPresenzaRevocataServiceImpl.class);
	eventPublisher.add(EventoRchiestaAnnullamentoPagamento.class, SottoscrittoreEventoRchiestaAnnullamentoPagamentoServiceImpl.class);
    }
}
