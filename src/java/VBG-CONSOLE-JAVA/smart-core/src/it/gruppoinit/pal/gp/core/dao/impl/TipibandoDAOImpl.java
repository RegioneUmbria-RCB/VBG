package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipibandoDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipibandoDAOImpl extends BaseDAOImpl<Tipibando, PkId> implements TipibandoDAO {

    @Override
    public Class<Tipibando> getEntityClass() {

	return Tipibando.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tipibando> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipibando> findActiveTipibando() {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.like("attivo", "1", MatchMode.EXACT));
	return (List<Tipibando>) getHibernateTemplate().findByCriteria(det);
    }
}
