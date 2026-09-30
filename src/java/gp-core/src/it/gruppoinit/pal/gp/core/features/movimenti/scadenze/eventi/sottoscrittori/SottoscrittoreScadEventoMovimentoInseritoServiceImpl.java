package it.gruppoinit.pal.gp.core.features.movimenti.scadenze.eventi.sottoscrittori;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ScadenzeEliminate;
import it.gruppoinit.pal.gp.core.domain.ScadenzeEliminateId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbit;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoMovimentoInserito;
import it.gruppoinit.pal.gp.core.features.movimenti.metadati.IMovimentiMetadatiDAO;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.IScadenzeEliminateDAO;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.rabbitmq.NotificaScadenzeRequest;
import it.gruppoinit.pal.gp.core.features.rabbitmq.CodaMessaggiRabbitService;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;

@Service
public class SottoscrittoreScadEventoMovimentoInseritoServiceImpl implements IEventSubscriber<EventoMovimentoInserito> {

    private MovimentiNoSecurityService movimentiNoSecurityService;
    private IMovimentiMetadatiDAO movimentiMetadatiDAO;
    private IScadenzeEliminateDAO scadenzeEliminateDAO;

    @Autowired
    public void setMovimentiMetadatiDAO(IMovimentiMetadatiDAO movimentiMetadatiDAO) {

	this.movimentiMetadatiDAO = movimentiMetadatiDAO;
    }

    @Autowired
    public void setMovimentiNoSecurityService(MovimentiNoSecurityService movimentiNoSecurityService) {

	this.movimentiNoSecurityService = movimentiNoSecurityService;
    }

    @Autowired
    public void setScadenzeEliminateDAO(IScadenzeEliminateDAO scadenzeEliminateDAO) {

	this.scadenzeEliminateDAO = scadenzeEliminateDAO;
    }

    @Autowired
    private CodaMessaggiRabbitService codaMessaggiRabbitService;

    @Override
    public void onEvent(EventoMovimentoInserito e) throws EventAbortedException {

	if (e == null) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoMovimentoInserito non riporta nessuna informazione");
	}
	if (e.getCodiceMovimento() == null) {
	    throw new RuntimeException(
		    "Impossibile proseguire in quanto l'evento EventoMovimentoInserito non riporta i riferimenti del movimento inserito");
	}
	//2. Verifico se presente il guid altrimenti non va preso in considerazione
	String uuid = this.movimentiMetadatiDAO.getUuid(e.getCodiceMovimento());
	if (StringUtils.isBlank(uuid)) {
	    return;
	}
	Movimenti mov = this.movimentiNoSecurityService.findById(new PkId(e.getCodiceMovimento()));
	//3. Se non è una scadenza
	if (!e.isScadenza()) {
	    //3.1 Verifico se è configurato per le notifiche rabbit
	    if (mov.getTipomovimento().getTipimovimentoRabbit() == null || mov.getTipomovimento().getTipimovimentoRabbit().isEmpty()) {
		return;
	    }
	    //3.2 Verifico se abilitato alla notifica delle scadenze
	    for (TipimovimentoRabbit rabbitConfig : mov.getTipomovimento().getTipimovimentoRabbit()) {
		if (RabbitTopicEnum.BACKEND_SCADENZE_EVASA.getValue().equalsIgnoreCase(rabbitConfig.getId().getTopic())) {
		    //3.2.1 Inserisco nella tabella scadenze_eliminate
		    String uuIdPratica = mov.getIstanza().getUuid();
		    ScadenzeEliminateId idScadenza = new ScadenzeEliminateId();
		    idScadenza.setIdcomune(ORMHelper.getIdcomune());
		    idScadenza.setUuid(uuid);
		    ScadenzeEliminate scadenza = new ScadenzeEliminate();
		    scadenza.setUuidIstanza(uuIdPratica);
		    scadenza.setSoftware(mov.getIstanza().getSoftware().getCodice());
		    scadenza.setId(idScadenza);
		    this.scadenzeEliminateDAO.insert(scadenza);
		    //3.2.2 Recupero uuid dell'istanza
		    //5. Creo la request
		    NotificaScadenzeRequest request = new NotificaScadenzeRequest(e.getCodiceMovimento()//
			    , mov.getIstanza().getSoftware().getCodice()//
			    , uuIdPratica // 
			    , uuid //
			    , mov.getTipomovimento().getId().getTipomovimento() //
			    , RabbitTopicEnum.BACKEND_SCADENZE_EVASA //
			    , mov.getIstanza().getId().getCodice());
		    //6. Verifico se va trasmessa una notifica tramite Rabbit Mq
		    codaMessaggiRabbitService.insertScadenza(request);
		}
	    }
	    return;
	}
	//3. Recupero uuid dell'istanza
	String uuIdPratica = mov.getIstanza().getUuid();
	//4. Creo la request
	NotificaScadenzeRequest request = new NotificaScadenzeRequest(e.getCodiceMovimento()//
		, mov.getIstanza().getSoftware().getCodice()//
		, uuIdPratica // 
		, uuid //
		, mov.getTipomovimento().getId().getTipomovimento() //
		, RabbitTopicEnum.BACKEND_SCADENZE_NUOVA //
		, mov.getIstanza().getId().getCodice());
	//6. Verifico se va trasmessa una notifica tramite Rabbit Mq
	codaMessaggiRabbitService.insertScadenza(request);
    }
}
