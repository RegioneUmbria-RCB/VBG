package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ElencocassaedilebaseDAO;
import it.gruppoinit.pal.gp.core.domain.Elencocassaedilebase;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ElencocassaedilebaseDAOImpl extends BaseDAOImpl<Elencocassaedilebase, String> implements ElencocassaedilebaseDAO {

    @Override
    public Class<Elencocassaedilebase> getEntityClass() {

	return Elencocassaedilebase.class;
    }

    @Override
    public List<Elencocassaedilebase> findByDescrizione(String descrizione) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (StringUtils.isNotBlank(descrizione)) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	det.addOrder(Order.asc("descrizione"));
	return (List<Elencocassaedilebase>) getHibernateTemplate().findByCriteria(det);
    }
}
