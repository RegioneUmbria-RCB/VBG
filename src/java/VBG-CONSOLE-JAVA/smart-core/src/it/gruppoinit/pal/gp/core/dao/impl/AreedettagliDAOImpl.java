package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AreedettagliDAO;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Areedettagli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author Luca Proietti
 * 
 */
@Repository
public class AreedettagliDAOImpl extends BaseDAOImpl<Areedettagli, PkId> implements AreedettagliDAO {

    @Override
    public Class<Areedettagli> getEntityClass() {

	return Areedettagli.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Areedettagli> findByAree(Aree area) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	DetachedCriteria stradarioCriteria = criteria.createCriteria("stradario");
	DetachedCriteria areeCrit = criteria.createCriteria("aree", Criteria.INNER_JOIN);
	areeCrit.add(Restrictions.eq("id", area.getId()));
	stradarioCriteria.addOrder(Order.asc("descrizione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Areedettagli> findByStradario(Stradario stradario) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("stradario", "_stradario");
	criteria.createAlias("aree", "_aree");
	criteria.add(Restrictions.eq("stradarioId", stradario.getId().getCodice()));
	criteria.add(Restrictions.or(Restrictions.eqProperty("_aree.comune.codicecomune", "_stradario.comune.codicecomune"),
		Restrictions.isNull("_stradario.comune.codicecomune")));
	criteria.addOrder(Order.asc("_stradario.descrizione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
