package it.gruppoinit.pal.gp.core.features.documenticondivisi.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.eventi.SottoscrittoreEventoIstanzaProtocollataIOAppServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.eventi.SottoscrittoreEventoMovimentoProtocollatoIOAppServiceImpl;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoIstanzaProtocollata;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoMovimentoProtocollato;

@Service
public class DocumentiCondivisiModuleServiceImpl extends EventBusModule {

    @Autowired
    public DocumentiCondivisiModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoIstanzaProtocollata.class, SottoscrittoreIstanzaProtocollataServiceImpl.class);
	eventPublisher.add(EventoMovimentoProtocollato.class, SottoscrittoreMovimentoProtocollatoServiceImpl.class);
	eventPublisher.add(EventoMovimentoProtocollato.class, SottoscrittoreEventoMovimentoProtocollatoIOAppServiceImpl.class);
	eventPublisher.add(EventoIstanzaProtocollata.class, SottoscrittoreEventoIstanzaProtocollataIOAppServiceImpl.class);
    }
}