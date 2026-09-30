package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CommedilizieTipologiedettDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedettId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class CommedilizieTipologiedettDAOImpl extends BaseDAOImpl<CommedilizieTipologiedett, CommedilizieTipologiedettId> implements
	CommedilizieTipologiedettDAO {

    @Override
    public Class<CommedilizieTipologiedett> getEntityClass() {

	return CommedilizieTipologiedett.class;
    }

    @Override
    public List<CommedilizieTipologiedett> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CommedilizieTipologiedett> findByTipologia(CommedilizieTipologie tipologia) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("id.codcommtipologia", tipologia.getId().getCodice()));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
