package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.domain.Comuni;

@Repository
public class ComuniDAOImpl extends BaseDAOImpl<Comuni, String> implements ComuniDAO {

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
    public List<Comuni> findByDescrizione(String comune, int maxResults) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (StringUtils.isNotBlank(comune)) {
	    det.add(Restrictions.or(Restrictions.ilike("comune", comune, MatchMode.START),
		    Restrictions.ilike("codicecomune", comune, MatchMode.START)));
	}
	det.addOrder(Order.asc("comune"));
	return (List<Comuni>) getHibernateTemplate().findByCriteria(det, 0, maxResults);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Comuni> findComuniItalianiByDescrizione(String comune, int maxResults) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (StringUtils.isNotBlank(comune)) {
	    det.add(Restrictions.or(Restrictions.ilike("comune", comune, MatchMode.START),
		    Restrictions.ilike("codicecomune", comune, MatchMode.START)));
	}
	det.add(Restrictions.ne("siglaprovincia", "EE"));
	det.addOrder(Order.asc("comune"));
	return (List<Comuni>) getHibernateTemplate().findByCriteria(det, 0, maxResults);
    }
}
