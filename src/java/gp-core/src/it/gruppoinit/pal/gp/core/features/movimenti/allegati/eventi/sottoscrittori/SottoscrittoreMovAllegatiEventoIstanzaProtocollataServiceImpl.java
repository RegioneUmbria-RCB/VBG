package it.gruppoinit.pal.gp.core.features.movimenti.allegati.eventi.sottoscrittori;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver.MovimentiAllegatiResolverFactoryService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.TipimovimentodoctipoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoIstanzaProtocollata;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

@Service
public class SottoscrittoreMovAllegatiEventoIstanzaProtocollataServiceImpl extends SottoscrittoreEventoMovimentoProtocollatoBase
	implements IEventSubscriber<EventoIstanzaProtocollata> {

    private static final Logger logger = LoggerFactory.getLogger(SottoscrittoreMovAllegatiEventoIstanzaProtocollataServiceImpl.class);
    private MovimentiallegatiService service;
    private IstanzeService istanzeService;
    private MovimentiService movimentiService;
    private TipimovimentodoctipoService docTipoService;
    private TempLinkallegatiService tempLinkallegatiService;
    private MovimentiAllegatiResolverFactoryService movimentiAllegatiResolverFactoryService;

    @Autowired
    public void setTempLinkallegatiService(TempLinkallegatiService tempLinkallegatiService) {

	this.tempLinkallegatiService = tempLinkallegatiService;
    }

    @Autowired
    public void setService(MovimentiallegatiService service) {

	this.service = service;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setDocTipoService(TipimovimentodoctipoService docTipoService) {

	this.docTipoService = docTipoService;
    }

    @Autowired
    public void setMovimentiAllegatiResolverFactoryService(MovimentiAllegatiResolverFactoryService movimentiAllegatiResolverFactoryService) {

	this.movimentiAllegatiResolverFactoryService = movimentiAllegatiResolverFactoryService;
    }

    @Override
    public void onEvent(EventoIstanzaProtocollata e) throws EventAbortedException {

	try {
	    //1. Recupero l'istanza
	    Istanze istanza = this.istanzeService.findById(new PkId(e.getCodiceIstanza()));
	    //2. Verifica istanza con intervento per domanda on line
	    if (!istanza.getAlberoproc().isPubblicaSoloDomandaOnLine()) {
		return;
	    }
	    //2. Recuperop il movimento di avvio
	    Movimenti mov = this.movimentiService.findMovimentoAvvioIstanza(istanza);
	    this.generaDocumentiAutomatici(docTipoService, movimentiAllegatiResolverFactoryService, service, mov, tempLinkallegatiService);
	} catch (Exception err) {
	    logger.error("Errore nella gestione dell'evento " + e, err);
	}
    }
}
