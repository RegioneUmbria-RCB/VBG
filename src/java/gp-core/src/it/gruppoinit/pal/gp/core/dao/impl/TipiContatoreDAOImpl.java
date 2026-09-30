package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiContatoreDAO;
import it.gruppoinit.pal.gp.core.domain.TipiContatore;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

@Repository
public class TipiContatoreDAOImpl extends BaseDAOImpl<TipiContatore, Integer> implements TipiContatoreDAO {

    @Override
    public Class<TipiContatore> getEntityClass() {

	return TipiContatore.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipiContatore> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
	return (List<TipiContatore>) getHibernateTemplate().findByCriteria(criteria);
    }
}
