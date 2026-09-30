package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoSoggettiesterniDAO;
import it.gruppoinit.pal.gp.core.domain.FoSoggettiesterni;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class FoSoggettiesterniDAOImpl extends BaseDAOImpl<FoSoggettiesterni, Integer> implements FoSoggettiesterniDAO {

    @Override
    public Class<FoSoggettiesterni> getEntityClass() {

	return FoSoggettiesterni.class;
    }

    @SuppressWarnings("unchecked")
    public List<FoSoggettiesterni> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
	criteria.addOrder(Order.asc("descrizione"));
	if (null != firstResult && null != maxResult) {
	    return getHibernateTemplate().findByCriteria(criteria, firstResult.intValue(), maxResult.intValue());
	} else {
	    return getHibernateTemplate().findByCriteria(criteria);
	}
    }
}
