package it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.eventi.EventoAttoInserito;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoAttoInseritoServiceImpl implements IEventSubscriber<EventoAttoInserito> {

    private AutorizzazioniMetadatiService metadatiService;

    @Autowired
    public void setMetadatiService(AutorizzazioniMetadatiService metadatiService) {

	this.metadatiService = metadatiService;
    }

    @Override
    public void onEvent(EventoAttoInserito e) {

	//this.metadatiService.insertMetadati(MetadatiInserimentoFactory.fromAttoInseritoType(e.getIdAutorizzazione, e.getAtto()));
    }
}
