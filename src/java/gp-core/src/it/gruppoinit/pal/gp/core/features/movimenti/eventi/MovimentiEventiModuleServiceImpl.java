package it.gruppoinit.pal.gp.core.features.movimenti.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.movimenti.allegati.eventi.sottoscrittori.SottoscrittoreMovAllegatiEventoIstanzaProtocollataServiceImpl;
import it.gruppoinit.pal.gp.core.features.movimenti.allegati.eventi.sottoscrittori.SottoscrittoreMovAllegatiEventoMovimentoInseritoServiceImpl;
import it.gruppoinit.pal.gp.core.features.movimenti.allegati.eventi.sottoscrittori.SottoscrittoreMovAllegatiEventoMovimentoProtocollatoServiceImpl;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.eventi.sottoscrittori.SottoscrittoreScadEventoMovimentoAbilitatoServiceImpl;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.eventi.sottoscrittori.SottoscrittoreScadEventoMovimentoAggiornatoServiceImpl;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.eventi.sottoscrittori.SottoscrittoreScadEventoMovimentoCancellatoServiceImpl;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.eventi.sottoscrittori.SottoscrittoreScadEventoMovimentoDisabilitatoServiceImpl;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.eventi.sottoscrittori.SottoscrittoreScadEventoMovimentoInseritoServiceImpl;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoIstanzaProtocollata;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoMovimentoProtocollato;

@Service
public class MovimentiEventiModuleServiceImpl extends EventBusModule {

    @Autowired
    public MovimentiEventiModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoErroreInCancellazioneMovimentodaIstanzaCollegata.class,
		EventiErroreCancellazioneMovimentiIstCollegateSottoscrittoreServiceImpl.class);
	eventPublisher.add(EventoMovimentoInserito.class, SottoscrittoreMovAllegatiEventoMovimentoInseritoServiceImpl.class);
	eventPublisher.add(EventoMovimentoProtocollato.class, SottoscrittoreMovAllegatiEventoMovimentoProtocollatoServiceImpl.class);
	eventPublisher.add(EventoIstanzaProtocollata.class, SottoscrittoreMovAllegatiEventoIstanzaProtocollataServiceImpl.class);
	eventPublisher.add(EventoMovimentoInserito.class, SottoscrittoreScadEventoMovimentoInseritoServiceImpl.class);
	eventPublisher.add(EventoMovimentoAggiornato.class, SottoscrittoreScadEventoMovimentoAggiornatoServiceImpl.class);
	eventPublisher.add(EventoMovimentoCancellato.class, SottoscrittoreScadEventoMovimentoCancellatoServiceImpl.class);
	eventPublisher.add(EventoMovimentoAbilitato.class, SottoscrittoreScadEventoMovimentoAbilitatoServiceImpl.class);
	eventPublisher.add(EventoMovimentoDisabilitato.class, SottoscrittoreScadEventoMovimentoDisabilitatoServiceImpl.class);
    }
}
