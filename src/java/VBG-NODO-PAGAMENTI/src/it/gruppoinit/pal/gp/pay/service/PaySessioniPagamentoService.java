/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;

/**
 * @author francol
 *
 */
public interface PaySessioniPagamentoService extends BaseService<PaySessioniPagamento, PkId> {

    public PaySessioniPagamento creaSessionePagamento(AttivaSessionePagamentoResponseType sessionData, List<PayPosizioniDebitorie> payPos)
	    throws PayException;

    public void validateSessionePagamento(PayPosizioniDebitorie pos) throws PayInvalidRequestException;

    public List<PaySessioniPagamento> findBySessionId(String sexId);

    public List<PaySessioniPagamento> findByDigest(String digest);

    public List<PaySessioniPagamento> findSessioniPerPosizioneDebitoria(Integer idPos);

    public List<PaySessioniPagamento> findSessioniAttivePerPosizioneDebitoria(Integer idPos);
}
