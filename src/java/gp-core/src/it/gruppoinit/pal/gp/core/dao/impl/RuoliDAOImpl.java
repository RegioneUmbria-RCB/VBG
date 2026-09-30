/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RuoliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Ruoli;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class RuoliDAOImpl extends BaseDAOImpl<Ruoli, PkId> implements RuoliDAO {

    @Override
    public Class<Ruoli> getEntityClass() {

	return Ruoli.class;
    }

    @SuppressWarnings("unchecked")
    public List<Ruoli> findByFilter(Ruoli ruoli) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (ruoli.getRuolo() != null && !ruoli.getRuolo().equals("") && !ruoli.getRuolo().equals("%")) {
	    det.add(Restrictions.ilike("ruolo", ruoli.getRuolo(), MatchMode.ANYWHERE));
	}
	det.addOrder(Order.asc("ruolo"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
