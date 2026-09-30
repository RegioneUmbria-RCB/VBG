/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.exception.PayException;

/**
 * @author francol
 *
 */
public interface PayPagamentiService extends BaseService<PayPagamenti, PkId> {

    public void registraAvvenutoPagamento(PayPagamenti payPag, PayPosizioniDebitorie payPos) throws PayException;
    
    public void registraAvvenutoPagamento(PayPagamenti payPag, PayPosizioniDebitorie payPos, String ricevutaXml) throws PayException;

    public PayPagamenti getPagamentoByPosizioneDebitoria(PayPosizioniDebitorie payPos);

    public void inserisciDatiPagamento(PayPagamenti datiPagamento, PayPosizioniDebitorie payPos) throws PayException;

    public Set<PayPagamenti> findByIdPosizioneDebitoria(Integer idPosizioneDebitoria);

    public int countByIdPosizioneDebitoria(Integer idPosizioneDebitoria);
}
