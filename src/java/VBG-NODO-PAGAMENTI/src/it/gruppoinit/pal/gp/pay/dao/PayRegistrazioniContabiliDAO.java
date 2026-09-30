/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;


/**
 * @author francol
 *
 */
public interface PayRegistrazioniContabiliDAO extends BaseDAO<PayRegistrazioniContabili, PkId> {

    int countPosizioniByIdRegistrazione(Integer idRegistrazioneContabile);

    List<Integer> findIdPosizioniByIdRegistrazione(Integer idRegistrazioneContabile);
}
