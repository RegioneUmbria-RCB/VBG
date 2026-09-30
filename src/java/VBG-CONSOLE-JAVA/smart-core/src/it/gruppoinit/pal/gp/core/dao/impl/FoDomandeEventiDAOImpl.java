/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomandeEventiDAO;
import it.gruppoinit.pal.gp.core.domain.FoDomandeEventi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francol
 *
 */
@Repository
public class FoDomandeEventiDAOImpl extends BaseDAOImpl<FoDomandeEventi, PkId> implements FoDomandeEventiDAO {

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl#getEntityClass()
     */
    @Override
    public Class<FoDomandeEventi> getEntityClass() {

	return FoDomandeEventi.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FoDomandeEventi> findByIdDomanda(String idComuneDomanda, Integer idDomanda) {

	DetachedCriteria crit = getIdcomuneCriteria(idComuneDomanda);
	crit.add(Restrictions.eq("fkDomanda", idDomanda));
	crit.addOrder(Order.asc("dataEvento"));
	List<FoDomandeEventi> eventi = getHibernateTemplate().findByCriteria(crit);
	return eventi;
    }
}
