package it.gruppoinit.pal.gp.core.features.movimenti.istanzecollegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.eventi.EventoIstanzaCollegata;

@Service
public class MovimentiPraticaPadreCollegamentoSottoscrittoreServiceImpl implements IEventSubscriber<EventoIstanzaCollegata> {

    @Autowired
    private IstanzecollegateService istanzecollegateService;

    @Override
    public void onEvent(EventoIstanzaCollegata e) {

	istanzecollegateService.insertMovimentoInIstanzaCollegata(e.getCodiceIstanzaOrigine(), e.getCodiceIstanzaDestinazione());
    }
}
