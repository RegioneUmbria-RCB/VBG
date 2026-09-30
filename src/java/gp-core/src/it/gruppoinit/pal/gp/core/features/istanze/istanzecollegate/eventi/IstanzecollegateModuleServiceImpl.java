package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.movimenti.istanzecollegate.MovimentiPraticaPadreCollegamentoSottoscrittoreServiceImpl;
import it.gruppoinit.pal.gp.core.features.movimenti.istanzecollegate.MovimentiPraticaPadreScollegamentoSottoscrittoreServiceImpl;

@Service
public class IstanzecollegateModuleServiceImpl extends EventBusModule {

    @Autowired
    public IstanzecollegateModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoIstanzaCollegata.class, MovimentiPraticaPadreCollegamentoSottoscrittoreServiceImpl.class);
	eventPublisher.add(EventoIstanzaScollegata.class, MovimentiPraticaPadreScollegamentoSottoscrittoreServiceImpl.class);
    }
}
