package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ComuniItalianiDAO;
import it.gruppoinit.pal.gp.core.domain.ComuniItaliani;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ComuniItalianiDAOImpl extends BaseDAOImpl<ComuniItaliani, String> implements ComuniItalianiDAO {

    @Override
    public Class<ComuniItaliani> getEntityClass() {

	return ComuniItaliani.class;
    }

    @Override
    public void delete(ComuniItaliani entity) {

	throw new UnsupportedOperationException();
    }

    @Override
    public void insert(ComuniItaliani entity) {

	throw new UnsupportedOperationException();
    }

    @Override
    public void update(ComuniItaliani entity) {

	throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ComuniItaliani findByCodiceComune(ComuniItaliani entity) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("codicecomune", entity.getCodicecomune()));
	List<ComuniItaliani> comuniList = (List<ComuniItaliani>) getHibernateTemplate().findByCriteria(det);
	if (comuniList.size() > 0) {
	    return comuniList.get(0);
	}
	return null;
    }

    @Override
    public List<ComuniItaliani> findByDescrizione(String comune) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (StringUtils.isNotBlank(comune)) {
	    det.add(Restrictions.or(Restrictions.ilike("comune", comune, MatchMode.START),
		    Restrictions.ilike("codicecomune", comune, MatchMode.START)));
	}
	det.addOrder(Order.asc("comune"));
	return (List<ComuniItaliani>) getHibernateTemplate().findByCriteria(det);
    }
}
