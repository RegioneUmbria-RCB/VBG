package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AzioniDAO;
import it.gruppoinit.pal.gp.core.domain.Azioni;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository
public class AzioniDAOImpl extends BaseDAOImpl<Azioni, Integer> implements AzioniDAO {

    @Override
    public Class<Azioni> getEntityClass() {

	return Azioni.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Azioni> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
	criteria.addOrder(Order.asc("azDescrizione"));
	return (List<Azioni>) getHibernateTemplate().findByCriteria(criteria);
    }
}
