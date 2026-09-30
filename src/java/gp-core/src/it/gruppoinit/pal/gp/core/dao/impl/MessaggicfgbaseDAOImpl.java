package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MessaggicfgbaseDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Messaggicfgbase;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

@Repository
public class MessaggicfgbaseDAOImpl extends BaseDAOImpl<Messaggicfgbase, String> implements MessaggicfgbaseDAO {

    @Override
    public Class<Messaggicfgbase> getEntityClass() {

	return Messaggicfgbase.class;
    }

    @Override
    public void insert(Messaggicfgbase entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Messaggicfgbase entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(Messaggicfgbase entity) {

	throw new NotImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Messaggicfgbase> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (null != firstResult && null != maxResult) {
	    return (List<Messaggicfgbase>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Messaggicfgbase>) getHibernateTemplate().findByCriteria(det);
	}
    }
}
