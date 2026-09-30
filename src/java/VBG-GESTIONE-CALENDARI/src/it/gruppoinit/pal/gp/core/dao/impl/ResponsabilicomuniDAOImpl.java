/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabilicomuniDAO;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author fabrizioc
 * 
 */
@Repository
public class ResponsabilicomuniDAOImpl extends BaseDAOImpl<Responsabilicomuni, ResponsabilicomuniId> implements ResponsabilicomuniDAO {

    @Override
    public Class<Responsabilicomuni> getEntityClass() {

	return Responsabilicomuni.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Responsabilicomuni> findByResponsabile(Responsabili responsabile) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	DetachedCriteria responsabileCrit = criteria.createCriteria("responsabile");
	responsabileCrit.add(Restrictions.eq("id", responsabile.getId()));
	DetachedCriteria comuneCritOrder = criteria.createCriteria("comune");
	comuneCritOrder.addOrder(Order.asc("comune"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Responsabilicomuni> findByComune(Comuni comune) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	DetachedCriteria comuneCrit = criteria.createCriteria("comune");
	comuneCrit.add(Restrictions.eq("codicecomune", comune.getCodicecomune()));
	DetachedCriteria responsabileCritOrder = criteria.createCriteria("responsabile");
	responsabileCritOrder.addOrder(Order.asc("responsabile"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
