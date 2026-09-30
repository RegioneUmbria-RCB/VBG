/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;


/**
 * @author francol
 *
 */
public interface PayStatoPagamentiDAO extends BaseDAO<PayStatoPagamenti, PkId> {

	public StatiPagamento findStatoByPosizioneDebitoria(PayPosizioniDebitorie posizioneDebitoria);
}
