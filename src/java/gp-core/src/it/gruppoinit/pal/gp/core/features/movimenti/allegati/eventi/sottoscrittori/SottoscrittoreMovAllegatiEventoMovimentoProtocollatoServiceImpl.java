package it.gruppoinit.pal.gp.core.features.movimenti.allegati.eventi.sottoscrittori;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver.MovimentiAllegatiResolverFactoryService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.TipimovimentodoctipoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoMovimentoProtocollato;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

@Service
public class SottoscrittoreMovAllegatiEventoMovimentoProtocollatoServiceImpl extends SottoscrittoreEventoMovimentoProtocollatoBase
	implements IEventSubscriber<EventoMovimentoProtocollato> {

    private static final Logger logger = LoggerFactory.getLogger(SottoscrittoreMovAllegatiEventoMovimentoProtocollatoServiceImpl.class);
    private MovimentiallegatiService service;
    private TipimovimentodoctipoService docTipoService;
    private MovimentiService movimentiService;
    private TempLinkallegatiService tempLinkallegatiService;
    private MovimentiAllegatiResolverFactoryService movimentiAllegatiResolverFactoryService;

    @Autowired
    public void setService(MovimentiallegatiService service) {

	this.service = service;
    }

    @Autowired
    public void setDocTipoService(TipimovimentodoctipoService docTipoService) {

	this.docTipoService = docTipoService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentiAllegatiResolverFactoryService(MovimentiAllegatiResolverFactoryService movimentiAllegatiResolverFactoryService) {

	this.movimentiAllegatiResolverFactoryService = movimentiAllegatiResolverFactoryService;
    }

    @Autowired
    public void setTempLinkallegatiService(TempLinkallegatiService tempLinkallegatiService) {

	this.tempLinkallegatiService = tempLinkallegatiService;
    }

    @Override
    public void onEvent(EventoMovimentoProtocollato e) throws EventAbortedException {

	try {
	    //1. Recuperop il tipomovimento
	    Movimenti mov = this.movimentiService.findById(new PkId(e.getCodiceMovimento()));
	    this.generaDocumentiAutomatici(docTipoService, movimentiAllegatiResolverFactoryService, service, mov, tempLinkallegatiService);
	} catch (Exception err) {
	    logger.error("Errore nella gestione dell'evento " + e, err);
	}
    }
}
