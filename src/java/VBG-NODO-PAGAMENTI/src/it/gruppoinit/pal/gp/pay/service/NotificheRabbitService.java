package it.gruppoinit.pal.gp.pay.service;

import java.util.Date;

import it.gruppoinit.pal.gp.pay.exception.ServizioRemotoException;
import it.gruppoinit.pal.gp.pay.service.helper.rabbit.model.RabbitAggiornaStatoPosizioneDebitoria;

public interface NotificheRabbitService {

    void notificaStatoRabbitMQ(Integer idPosizione, String cfCodiceProfilo, Integer idPayStatoPagamenti) throws Exception;

    void notificaAggiornamentoDataScadenza(Integer idPosizioneDebitoria, Date dataScadenza) throws ServizioRemotoException;

    RabbitAggiornaStatoPosizioneDebitoria popolaBeanAggiornamentoStato(Integer idPayStatoPagamenti, String alias, String cfEnteCreditore);
}
