package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneConcessioneEliminata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneInserita;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckModificaOccupante;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoConcessioneInserita;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoOccupanteModificato;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoSubentroEffettuato;
import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;

@Service
public class AbbonamentoModuleServiceImpl extends EventBusModule {

    @Autowired
    public AbbonamentoModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoSubentroEffettuato.class, SottoscrittoreAbbonamentiCheckSubentroServiceImpl.class);
	eventPublisher.add(EventoCheckModificaOccupante.class, SottoscrittoreAbbonamentoEventoCheckModificaOccupanteServiceImpl.class);
	eventPublisher.add(EventoOccupanteModificato.class, SottoscrittoreAbbonamentoEventoOccupanteModificatoServiceImpl.class);
	eventPublisher.add(EventoConcessioneInserita.class, SottoscrittoreAbbonamentiConcInseritaServiceImpl.class);
	eventPublisher.add(EventoAutorizzazioneInserita.class, SottoscrittoreAbbonamentiAutInseritaServiceImpl.class);
	eventPublisher.add(EventoAutorizzazioneConcessioneEliminata.class, SottoscrittoreAbbonamentoEventoAutConcEliminataServiceImpl.class);
	eventPublisher.add(EventoMovimentoInserito.class, SottoscrittoreEventoMovInseritoServiceImpl.class);
	eventPublisher.add(EventoOperazioneAutorizzazione.class, SottoscrittoreEventoOpeAutServiceImpl.class);
    }
}
