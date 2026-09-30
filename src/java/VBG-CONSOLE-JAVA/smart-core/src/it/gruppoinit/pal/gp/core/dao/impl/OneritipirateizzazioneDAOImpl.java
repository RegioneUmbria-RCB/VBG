package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OneritipirateizzazioneDAO;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class OneritipirateizzazioneDAOImpl extends BaseDAOImpl<Oneritipirateizzazione, PkId> implements OneritipirateizzazioneDAO {

    @Override
    public Class<Oneritipirateizzazione> getEntityClass() {

	return Oneritipirateizzazione.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Oneritipirateizzazione> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Oneritipirateizzazione> findAllSenzaInteressiLegali() {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("flagInteressiLegali", false));
	return getHibernateTemplate().findByCriteria(det);
    }
}
