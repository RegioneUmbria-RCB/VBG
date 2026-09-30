package it.gruppoinit.pal.gp.core.features.movimenti.scadenze.eventi.sottoscrittori;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoMovimentoDisabilitato;
import it.gruppoinit.pal.gp.core.features.movimenti.metadati.IMovimentiMetadatiDAO;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.rabbitmq.NotificaScadenzeRequest;
import it.gruppoinit.pal.gp.core.features.rabbitmq.CodaMessaggiRabbitService;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

@Service
public class SottoscrittoreScadEventoMovimentoDisabilitatoServiceImpl implements IEventSubscriber<EventoMovimentoDisabilitato> {

    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private IMovimentiMetadatiDAO movimentiMetadatiDAO;
    @Autowired
    private CodaMessaggiRabbitService codaMessaggiRabbitService;

    @Override
    public void onEvent(EventoMovimentoDisabilitato e) throws EventAbortedException {

	if (e == null) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoMovimentoAbilitato non riporta nessuna informazione");
	}
	if (e.getCodiceMovimento() == null) {
	    throw new RuntimeException(
		    "Impossibile proseguire in quanto l'evento EventoMovimentoAggiornato non riporta il riferimento del movimento");
	}
	String uuid = this.movimentiMetadatiDAO.getUuid(e.getCodiceMovimento());
	if (StringUtils.isBlank(uuid)) {
	    return;
	}
	//3. Recupero uuid dell'istanza
	Movimenti mov = this.movimentiService.findById(new PkId(e.getCodiceMovimento()));
	String uuIdPratica = mov.getIstanza().getUuid();
	NotificaScadenzeRequest request = new NotificaScadenzeRequest(e.getCodiceMovimento()//
		, mov.getIstanza().getSoftware().getCodice()//
		, uuIdPratica // 
		, uuid //
		, mov.getTipomovimento().getId().getTipomovimento() //
		, RabbitTopicEnum.BACKEND_SCADENZE_ELIMINATA //
		, mov.getIstanza().getId().getCodice());
	//6. Verifico se va trasmessa una notifica tramite Rabbit Mq
	codaMessaggiRabbitService.insertScadenza(request);
    }
}
