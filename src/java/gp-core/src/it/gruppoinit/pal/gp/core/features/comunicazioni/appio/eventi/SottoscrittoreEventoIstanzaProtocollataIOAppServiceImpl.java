package it.gruppoinit.pal.gp.core.features.comunicazioni.appio.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaService;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoIstanzaProtocollata;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;

@Service
public class SottoscrittoreEventoIstanzaProtocollataIOAppServiceImpl implements IEventSubscriber<EventoIstanzaProtocollata> {

    @Autowired
    private IAppIoCodaService appIoCodaService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MovimentiNoSecurityService movimentiService;

    @Override
    public void onEvent(EventoIstanzaProtocollata e) throws EventAbortedException {

	Integer codiceIstanza = e.getCodiceIstanza();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	Movimenti movAvvioIstanza = movimentiService.findMovimentoAvvioIstanza(istanza);
	this.appIoCodaService.nuovoMessaggio(movAvvioIstanza.getId().getCodice());
    }
}
