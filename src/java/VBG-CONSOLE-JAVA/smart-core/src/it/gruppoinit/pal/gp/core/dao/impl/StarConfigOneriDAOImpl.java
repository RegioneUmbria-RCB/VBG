/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.StarConfigOneriDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StarConfigOneri;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francol
 *
 */
@Repository
public class StarConfigOneriDAOImpl extends BaseDAOImpl<StarConfigOneri, PkId> implements StarConfigOneriDAO {

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl#getEntityClass()
     */
    @Override
    public Class<StarConfigOneri> getEntityClass() {

	return StarConfigOneri.class;
    }

    @Override
    public StarConfigOneri findConfigOneriByCodiceComune(String codCOmune) {

	StarConfigOneri retVal = null;
	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("codiceComune", codCOmune));
	List<StarConfigOneri> results = getHibernateTemplate().findByCriteria(crit);
	if (results.size() > 0) {
	    retVal = results.get(0);
	}
	return retVal;
    }
    
    @Override
    public StarConfigOneri findConfigOneriBase() {

	StarConfigOneri retVal = null;
	DetachedCriteria crit = getIdcomunebaseCriteria();
	List<StarConfigOneri> results = getHibernateTemplate().findByCriteria(crit);
	if (results.size() > 0) {
	    retVal = results.get(0);
	}
	return retVal;
    }
}
