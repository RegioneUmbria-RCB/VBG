package it.gruppoinit.pal.gp.core.features.movimenti.scadenze.eventi.sottoscrittori;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ScadenzeEliminate;
import it.gruppoinit.pal.gp.core.domain.ScadenzeEliminateId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoMovimentoCancellato;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.IScadenzeEliminateDAO;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.rabbitmq.NotificaScadenzeRequest;
import it.gruppoinit.pal.gp.core.features.rabbitmq.CodaMessaggiRabbitService;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

@Service
public class SottoscrittoreScadEventoMovimentoCancellatoServiceImpl implements IEventSubscriber<EventoMovimentoCancellato> {

    @Autowired
    private IScadenzeEliminateDAO scadenzeEliminateDAO;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private CodaMessaggiRabbitService codaMessaggiRabbitService;

    @Override
    public void onEvent(EventoMovimentoCancellato e) throws EventAbortedException {

	if (e == null) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoMovimentoCancellato non riporta nessuna informazione");
	}
	//1. Verifico se presente il guid altrimenti non va preso in considerazione
	if (StringUtils.isBlank(e.getUuid())) {
	    return;
	}
	//2. Verifico se è stata cancellata una scadenza altrimenti non va preso in considerazione
	if (!e.isScadenza()) {
	    return;
	}
	Istanze i = this.istanzeService.findById(new PkId(e.getCodiceIstanza()));
	String uuIdPratica = i.getUuid();
	//3. Inserisco nella tabella scadenze_eliminate
	ScadenzeEliminateId idScadenza = new ScadenzeEliminateId();
	idScadenza.setIdcomune(ORMHelper.getIdcomune());
	idScadenza.setUuid(e.getUuid());
	ScadenzeEliminate scadenza = new ScadenzeEliminate();
	scadenza.setUuidIstanza(uuIdPratica);
	scadenza.setSoftware(i.getSoftware().getCodice());
	scadenza.setId(idScadenza);
	this.scadenzeEliminateDAO.insert(scadenza);
	//4. Recupero uuid dell'istanza
	//5. Creo la request
	NotificaScadenzeRequest request = new NotificaScadenzeRequest(e.getCodiceMovimento()//
		, i.getSoftware().getCodice() //
		, uuIdPratica // 
		, e.getUuid() //
		, e.getTipomovimento() //
		, RabbitTopicEnum.BACKEND_SCADENZE_ELIMINATA //
		, e.getCodiceIstanza());
	//6. Verifico se va trasmessa una notifica tramite Rabbit Mq
	codaMessaggiRabbitService.insertScadenza(request);
    }
}
