package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ElencoinailbaseDAO;
import it.gruppoinit.pal.gp.core.domain.Elencoinailbase;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ElencoinailbaseDAOimpl extends BaseDAOImpl<Elencoinailbase, String> implements ElencoinailbaseDAO {

    @Override
    public Class<Elencoinailbase> getEntityClass() {

	return Elencoinailbase.class;
    }

    @Override
    public List<Elencoinailbase> findByDescrizione(String descrizione) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (StringUtils.isNotBlank(descrizione)) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.START));
	}
	det.addOrder(Order.asc("descrizione"));
	return (List<Elencoinailbase>) getHibernateTemplate().findByCriteria(det);
    }
}
