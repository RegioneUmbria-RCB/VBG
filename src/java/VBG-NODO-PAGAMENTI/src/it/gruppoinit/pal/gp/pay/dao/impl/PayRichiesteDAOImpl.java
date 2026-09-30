/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayRichiesteDAO;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;

/**
 * @author francol
 *
 */
@Repository
public class PayRichiesteDAOImpl extends BaseDAOImpl<PayRichieste, PkId> implements PayRichiesteDAO {

    @Override
    public Class<PayRichieste> getEntityClass() {

	return PayRichieste.class;
    }
}
