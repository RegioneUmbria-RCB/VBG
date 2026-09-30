package it.gruppoinit.pal.gp.core.features.comunicazioni.appio.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaService;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoMovimentoProtocollato;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

@Service
public class SottoscrittoreEventoMovimentoProtocollatoIOAppServiceImpl implements IEventSubscriber<EventoMovimentoProtocollato> {

    private IAppIoCodaService appIoCodaService;

    @Autowired
    public SottoscrittoreEventoMovimentoProtocollatoIOAppServiceImpl(MovimentiService movimentiService, IAppIoCodaService appIoCodaService) {

	this.appIoCodaService = appIoCodaService;
    }

    @Override
    public void onEvent(EventoMovimentoProtocollato e) {

	Integer codiceMovimento = e.getCodiceMovimento();
	this.appIoCodaService.nuovoMessaggio(codiceMovimento);
    }
}
