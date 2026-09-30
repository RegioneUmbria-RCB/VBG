package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RangeRateizzazioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository
public class RangeRateizzazioniDAOImpl extends BaseDAOImpl<RangeRateizzazioni, PkId> implements RangeRateizzazioniDAO {

    @Override
    public Class<RangeRateizzazioni> getEntityClass() {

	return RangeRateizzazioni.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RangeRateizzazioni> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.addOrder(Order.asc("rangeBasso"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
