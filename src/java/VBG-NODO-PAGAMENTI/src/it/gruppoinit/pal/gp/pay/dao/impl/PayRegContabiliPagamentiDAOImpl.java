/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayRegContabiliPagamentiDAO;
import it.gruppoinit.pal.gp.pay.domain.PayRegContabiliPagamenti;

/**
 * @author francol
 *
 */
@Repository
public class PayRegContabiliPagamentiDAOImpl extends BaseDAOImpl<PayRegContabiliPagamenti, PkId> implements PayRegContabiliPagamentiDAO {

    @Override
    public Class<PayRegContabiliPagamenti> getEntityClass() {

	return null;
    }
}
