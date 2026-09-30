/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.PecInboxAllegatiDAO;
import it.gruppoinit.pal.gp.core.domain.PecInboxAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * @author francol
 * 
 */
@Repository
public class PecInboxAllegatiDAOImpl extends BaseDAOImpl<PecInboxAllegati, PkId> implements PecInboxAllegatiDAO {

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.PecInboxAllegatiDAO#findAllegatiPec(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PecInboxAllegati> findAllegatiPec(String codicePec) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("pec.id.id", codicePec));
	List<PecInboxAllegati> results = (List<PecInboxAllegati>) getHibernateTemplate().findByCriteria(crit);
	return results;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl#getEntityClass()
     */
    @Override
    public Class<PecInboxAllegati> getEntityClass() {

	return PecInboxAllegati.class;
    }
}
