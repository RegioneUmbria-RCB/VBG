package it.gruppoinit.pal.gp.pay.connector.fvgpay.service;

import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiRegistrazioneType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiRevocaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.StatoPagamentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;

public interface FvgPayBackendService {

    /**
     * Recupera le posizioni debitorie e gestisce la revoca
     * 
     * @param request
     */
    void gestisciEsitoRevocaPagamento(NotificaEsitiRevocaType request);

    /**
     * Recupera le posizioni debitorie e gestisce l'esito della registrazione
     * 
     * @param request
     */
    void gestisciNotificaEsitiRegistrazione(NotificaEsitiRegistrazioneType request);

    /**
     * Recupera le posizioni debitorie e gestisce l'esito dei pagamenti avvenuti o meno
     * 
     * @param request
     * @throws PayException
     */
    void gestisciNotificaEsitiPagamento(NotificaEsitiPagamentoType request) throws PayException;

    StatoPosizioneType gestisciStatoPagamento(PayPosizioniDebitorie payPos, StatoPagamentoPosizioneDebitoriaType spd) throws PayException;
}
