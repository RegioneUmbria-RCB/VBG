/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayPagamentiDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;

/**
 * @author francol
 *
 */
@Repository
public class PayPagamentiDAOImpl extends BaseDAOImpl<PayPagamenti, PkId> implements PayPagamentiDAO {

    @Override
    public Class<PayPagamenti> getEntityClass() {

	return PayPagamenti.class;
    }
}
