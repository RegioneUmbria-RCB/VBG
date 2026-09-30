package it.gruppoinit.pal.gp.core.features.movimenti.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;

@Service
public class EventiErroreCancellazioneMovimentiIstCollegateSottoscrittoreServiceImpl
	implements IEventSubscriber<EventoErroreInCancellazioneMovimentodaIstanzaCollegata> {

    @Autowired
    private IstanzeeventiService istanzeeventiService;

    @Override
    public void onEvent(EventoErroreInCancellazioneMovimentodaIstanzaCollegata e) {

	istanzeeventiService.insertEventoErroreInCancellazioneMovimentoDaIstanzeCollegate(e.getCodiceIstanzaOrigine(),
		e.getCodiceIstanzaDestinazione(), e.getCodiceMovimento(), e.getMessaggio());
    }
}
