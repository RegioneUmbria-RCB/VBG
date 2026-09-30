/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PaySoggettiDebitoriDAO;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;

/**
 * @author francol
 *
 */
@Repository
public class PaySoggettiDebitoriDAOImpl extends BaseDAOImpl<PaySoggettiDebitori, PkId> implements PaySoggettiDebitoriDAO {

    @Override
    public Class<PaySoggettiDebitori> getEntityClass() {

	return PaySoggettiDebitori.class;
    }

    @Override
    public List<PaySoggettiDebitori> findByCodiceFiscale(String cfpi, Boolean attivo) {

	List<PaySoggettiDebitori> profDebs = new ArrayList<PaySoggettiDebitori>();
	if (StringUtils.isNotBlank(cfpi)) {
	    DetachedCriteria crit = getIdcomuneCriteria();
	    crit.add(Restrictions.eq("cfPi", cfpi));
	    if (attivo != null) {
		crit.add(Restrictions.eq("flagAttivo", attivo));
	    }
	    profDebs = (List<PaySoggettiDebitori>) getHibernateTemplate().findByCriteria(crit);
	}
	return profDebs;
    }
    /*
    @Override
    public PaySoggettiDebitori findAttivoByCodiceFiscale(String cfpi) {
    
    PaySoggettiDebitori soggAttivo = null;
    List<PaySoggettiDebitori> profDebs = this.findByCodiceFiscale(cfpi, Boolean.TRUE);
    if (!profDebs.isEmpty()) {
        if (profDebs.size() == 1) {
    	soggAttivo = profDebs.get(0);
        } 
        else {
    	throw new PayConfigurationException("Esistono più profili attivi per il soggetto debitore con codice fiscale " + cfpi);
        }
    }
    return soggAttivo;
    }
    */
}
