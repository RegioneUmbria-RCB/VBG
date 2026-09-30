/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;

/**
 * @author francol
 *
 */
public interface PaySessioniPagamentoDAO extends BaseDAO<PaySessioniPagamento, PkId> {

    public List<PaySessioniPagamento> findByIdSessione(String idSes);
    
    public List<PaySessioniPagamento> findByDigest(String digest);
    
    public List<PaySessioniPagamento> findSessioniAttivePerPosizioneDebitoria(Integer idPos);

    public List<PaySessioniPagamento> findSessioniPerPosizioneDebitoria(Integer idPos);
}
