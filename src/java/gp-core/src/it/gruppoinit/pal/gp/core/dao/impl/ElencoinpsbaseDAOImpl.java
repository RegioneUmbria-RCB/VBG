package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ElencoinpsbaseDAO;
import it.gruppoinit.pal.gp.core.domain.Elencoinpsbase;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ElencoinpsbaseDAOImpl extends BaseDAOImpl<Elencoinpsbase, String> implements ElencoinpsbaseDAO {

    @Override
    public Class<Elencoinpsbase> getEntityClass() {

	return Elencoinpsbase.class;
    }

    @Override
    public List<Elencoinpsbase> findByDescrizione(String descrizione) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (StringUtils.isNotBlank(descrizione)) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.START));
	}
	det.addOrder(Order.asc("descrizione"));
	return (List<Elencoinpsbase>) getHibernateTemplate().findByCriteria(det);
    }
}
