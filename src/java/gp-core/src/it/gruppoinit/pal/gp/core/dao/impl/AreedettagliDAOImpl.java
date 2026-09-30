package it.gruppoinit.pal.gp.core.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AreedettagliDAO;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Areedettagli;
import it.gruppoinit.pal.gp.core.domain.PkId;

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
    public List<Areedettagli> findByStradario(Integer codiceStradario) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("stradario", "_stradario");
	criteria.createAlias("aree", "_aree");
	criteria.add(Restrictions.eq("stradarioId", codiceStradario));
	criteria.add(Restrictions.or(Restrictions.eqProperty("_aree.comune.codicecomune", "_stradario.comune.codicecomune"),
		Restrictions.isNull("_stradario.comune.codicecomune")));
	criteria.addOrder(Order.asc("_stradario.descrizione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Areedettagli> findByStradarioECivico(Integer codiceStradario, Integer civico) {

	if (codiceStradario == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare il metodo AreedettagliDAOImpl.findByStradarioECivico senza valorizzare il codiceStradario");
	}
	if (civico == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare il metodo AreedettagliDAOImpl.findByStradarioECivico senza valorizzare il civico");
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("stradario", "_stradario");
	criteria.createAlias("aree", "_aree");
	criteria.add(Restrictions.eq("stradarioId", codiceStradario));
	criteria.add(Restrictions.ge("civicoA", civico));
	criteria.add(Restrictions.le("civicoDa", civico));
	criteria.add(Restrictions.or(Restrictions.eqProperty("_aree.comune.codicecomune", "_stradario.comune.codicecomune"),
		Restrictions.isNull("_stradario.comune.codicecomune")));
	criteria.addOrder(Order.asc("_stradario.descrizione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Areedettagli> findByStradarioEKm(Integer codiceStradario, BigDecimal km) {

	if (codiceStradario == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare il metodo AreedettagliDAOImpl.findByStradarioEKm senza valorizzare il codiceStradario");
	}
	if (km == null) {
	    throw new IllegalArgumentException("Impossibile richiamare il metodo AreedettagliDAOImpl.findByStradarioEKm senza valorizzare il km");
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("stradario", "_stradario");
	criteria.createAlias("aree", "_aree");
	criteria.add(Restrictions.eq("stradarioId", codiceStradario));
	criteria.add(Restrictions.ge("kmA", km));
	criteria.add(Restrictions.le("kmDa", km));
	criteria.add(Restrictions.or(Restrictions.eqProperty("_aree.comune.codicecomune", "_stradario.comune.codicecomune"),
		Restrictions.isNull("_stradario.comune.codicecomune")));
	criteria.addOrder(Order.asc("_stradario.descrizione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
