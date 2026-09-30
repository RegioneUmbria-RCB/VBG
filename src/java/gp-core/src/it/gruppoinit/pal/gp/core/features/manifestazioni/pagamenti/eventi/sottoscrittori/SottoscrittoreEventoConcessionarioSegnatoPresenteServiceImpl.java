package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoConcessionarioSegnatoPresente;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaInserita;

@Service
public class SottoscrittoreEventoConcessionarioSegnatoPresenteServiceImpl implements IEventSubscriber<EventoConcessionarioSegnatoPresente> {

    private MercatipresenzeDService mercatipresenzeDService;
    private IEventPublisher eventPublisher;

    @Autowired
    public void setMercatipresenzeDService(MercatipresenzeDService mercatipresenzeDService) {

	this.mercatipresenzeDService = mercatipresenzeDService;
    }

    @Autowired
    public void setEventPublisher(IEventPublisher eventPublisher) {

	this.eventPublisher = eventPublisher;
    }

    @Override
    public void onEvent(EventoConcessionarioSegnatoPresente e) {

	EventoPresenzaInserita presenza = new EventoPresenzaInserita(this.mercatipresenzeDService.findById(new PkId(e.getIdMercatiPresenzeD())));
	this.eventPublisher.publish(presenza);
    }
}
