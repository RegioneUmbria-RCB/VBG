package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ManifestazioniDAO;
import it.gruppoinit.pal.gp.core.domain.Manifestazioni;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ManifestazioniDAOImpl extends BaseDAOImpl<Manifestazioni, Integer> implements ManifestazioniDAO {

    @Override
    public Class<Manifestazioni> getEntityClass() {

	return Manifestazioni.class;
    }

    @SuppressWarnings("unchecked")
    public List<Manifestazioni> findAll(Integer firstResult, Integer maxResult) {

	if (null != firstResult && null != maxResult) {
	    return (List<Manifestazioni>) getHibernateTemplate().findByExample(new Manifestazioni(), firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Manifestazioni>) getHibernateTemplate().findByExample(new Manifestazioni());
	}
    }
}
