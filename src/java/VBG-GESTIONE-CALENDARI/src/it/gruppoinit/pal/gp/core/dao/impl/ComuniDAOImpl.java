package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.domain.Comuni;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ComuniDAOImpl extends BaseDAOImpl<Comuni, String> implements ComuniDAO {

    @Override
    @SuppressWarnings("unchecked")
    public Comuni findByCodiceComune(Comuni entity) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("codicecomune", entity.getCodicecomune()));
	List<Comuni> comuniList = (List<Comuni>) getHibernateTemplate().findByCriteria(det);
	if (comuniList.size() > 0) {
	    return comuniList.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Comuni> findByDescrizione(String comune) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (StringUtils.isNotBlank(comune)) {
	    det.add(Restrictions.or(Restrictions.ilike("comune", comune, MatchMode.START),
		    Restrictions.ilike("codicecomune", comune, MatchMode.START)));
	}
	det.addOrder(Order.asc("comune"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public Class<Comuni> getEntityClass() {

	return Comuni.class;
    }

    @Override
    public void delete(Comuni entity) {

	throw new UnsupportedOperationException();
    }

    @Override
    public void insert(Comuni entity) {

	throw new UnsupportedOperationException();
    }

    @Override
    public void update(Comuni entity) {

	throw new UnsupportedOperationException();
    }
}
