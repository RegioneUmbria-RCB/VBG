package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LottiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Lotti;
import it.gruppoinit.pal.gp.core.domain.LottiId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class LottiDAOImpl extends BaseDAOImpl<Lotti, LottiId> implements LottiDAO {

    @Override
    public Class<Lotti> getEntityClass() {

	return Lotti.class;
    }

    @Override
    public List<Lotti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Lotti> findByAree(Aree aree) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("aree", aree));
	det.addOrder(Order.asc("id.codicelotto"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
