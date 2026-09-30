package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OnericomportamentoDAO;
import it.gruppoinit.pal.gp.core.domain.Onericomportamento;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class OnericomportamentoDAOImpl extends BaseDAOImpl<Onericomportamento, Integer> implements OnericomportamentoDAO {

    @Override
    public Class<Onericomportamento> getEntityClass() {

	return Onericomportamento.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Onericomportamento> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (null != firstResult && null != maxResult) {
	    return (List<Onericomportamento>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Onericomportamento>) getHibernateTemplate().findByCriteria(det);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Onericomportamento> findByDescrizione(String descrizione) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.ilike("comportamento", descrizione, MatchMode.ANYWHERE));
	det.addOrder(Order.asc("comportamento"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
