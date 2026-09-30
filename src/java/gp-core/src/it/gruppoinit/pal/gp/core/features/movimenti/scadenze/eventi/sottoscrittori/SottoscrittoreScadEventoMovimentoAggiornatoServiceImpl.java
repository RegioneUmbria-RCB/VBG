package it.gruppoinit.pal.gp.core.features.movimenti.scadenze.eventi.sottoscrittori;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.movimenti.FaseMovimentoEnum;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoMovimentoAggiornato;
import it.gruppoinit.pal.gp.core.features.movimenti.metadati.IMovimentiMetadatiDAO;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.rabbitmq.NotificaScadenzeRequest;
import it.gruppoinit.pal.gp.core.features.rabbitmq.CodaMessaggiRabbitService;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;

@Service
public class SottoscrittoreScadEventoMovimentoAggiornatoServiceImpl implements IEventSubscriber<EventoMovimentoAggiornato> {

    @Autowired
    private MovimentiNoSecurityService movimentiNoSecurityService;
    @Autowired
    private IMovimentiMetadatiDAO movimentiMetadatiDAO;
    @Autowired
    private CodaMessaggiRabbitService codaMessaggiRabbitService;

    @Override
    public void onEvent(EventoMovimentoAggiornato e) throws EventAbortedException {

	if (e == null) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoMovimentoAggiornato non riporta nessuna informazione");
	}
	if (e.getFase() == null) {
	    throw new RuntimeException(
		    "Impossibile proseguire in quanto l'evento EventoMovimentoAggiornato non riporta la fase iniziale del movimento");
	}
	//1. Se non riguarda una scadenza, non faccio nulla
	if (!e.getFase().equals(FaseMovimentoEnum.SCADENZA) && !e.getFase().equals(FaseMovimentoEnum.DA_SCADENZA_A_MOVIMENTO)) {
	    return;
	}
	if (e.getCodiceMovimento() == null) {
	    throw new RuntimeException(
		    "Impossibile proseguire in quanto l'evento EventoMovimentoAggiornato non riporta il riferimento del movimento");
	}
	//2. Verifico se presente il guid altrimenti non va preso in considerazione
	String uuid = this.movimentiMetadatiDAO.getUuid(e.getCodiceMovimento());
	if (StringUtils.isBlank(uuid)) {
	    return;
	}
	//3. Recupero uuid dell'istanza
	Movimenti mov = this.movimentiNoSecurityService.findById(new PkId(e.getCodiceMovimento()));
	String uuIdPratica = mov.getIstanza().getUuid();
	//4. Creo la request
	// Integer codiceMovimento, String software, String uuidPratica, String uuIdMovimento, String tipoMovimento, RabbitTopicEnum topic
	NotificaScadenzeRequest request = new NotificaScadenzeRequest(mov.getId().getCodice() //
		, mov.getIstanza().getSoftware().getCodice() //
		, uuIdPratica // 
		, uuid //
		, mov.getTipomovimento().getId().getTipomovimento() //
		, RabbitTopicEnum.BACKEND_SCADENZE_AGGIORNATA //
		, mov.getIstanza().getId().getCodice());
	//6. Verifico se va trasmessa una notifica tramite Rabbit Mq
	codaMessaggiRabbitService.insertScadenza(request);
    }
}
