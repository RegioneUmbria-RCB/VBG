package it.gruppoinit.pal.gp.core.features.oneri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoPosizioneDebitoriaPagata;

@Service
public class SottoscrittorePosizioneDebitoriaPagataServiceImpl implements IEventSubscriber<EventoPosizioneDebitoriaPagata> {

    IstanzeoneriService istanzeoneriService;

    @Autowired
    public SottoscrittorePosizioneDebitoriaPagataServiceImpl(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Override
    public void onEvent(EventoPosizioneDebitoriaPagata e) {

	this.istanzeoneriService.registraPagamentoAvvenutoByIdPosizioneDebitoria(e.getDatiPagamento(), e.getCfEnteCreditore());
    }
}
