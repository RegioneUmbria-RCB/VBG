package it.gruppoinit.pal.gp.core.features.rabbitmq.eventi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.istanze.eventi.EventoSoggettiIstanzaAggiornati;
import it.gruppoinit.pal.gp.core.features.istanze.eventi.NotificaSoggettiIstanzaAggiornatiRequest;
import it.gruppoinit.pal.gp.core.features.rabbitmq.CodaMessaggiRabbitService;

@Service
public class SottoscrittoreRMQEventoSoggettiIstanzaAggiornatiServiceImpl implements IEventSubscriber<EventoSoggettiIstanzaAggiornati> {

    private static final Logger log = LoggerFactory.getLogger(SottoscrittoreRMQEventoSoggettiIstanzaAggiornatiServiceImpl.class);
    @Autowired
    private IstanzeDAO istanzeDAO;
    @Autowired
    private CodaMessaggiRabbitService codaMessaggiRabbitService;

    @Override
    public void onEvent(EventoSoggettiIstanzaAggiornati e) throws EventAbortedException {

	if (e == null) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoSoggettiIstanzaAggiornati non riporta nessuna informazione");
	}
	if (e.getCodiceIstanza() == null) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoSoggettiIstanzaAggiornati non riporta il codice istanza");
	}
	//lanciamo notifica a api
	Istanze istanza = istanzeDAO.findById(new PkId(e.getCodiceIstanza()));
	NotificaSoggettiIstanzaAggiornatiRequest request = new NotificaSoggettiIstanzaAggiornatiRequest(e.getCodiceIstanza(),
		istanza.getSoftware().getCodice(), istanza.getUuid());
	try {
	    codaMessaggiRabbitService.insertSoggettiAggiornati(request);
	} catch (Exception ex) {
	    log.error("Errore nella notifica dell'aggiornamento soggetti della pratica", ex);
	}
    }
}
