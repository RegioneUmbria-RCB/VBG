package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OggettiDaCancellareDAO;
import it.gruppoinit.pal.gp.core.domain.OggettiDaCancellare;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class OggettiDaCancellareDAOImpl extends BaseDAOImpl<OggettiDaCancellare, PkId> implements OggettiDaCancellareDAO {

    @Override
    public Class<OggettiDaCancellare> getEntityClass() {

	return OggettiDaCancellare.class;
    }

    @Override
    public List<OggettiDaCancellare> findByCodiceOggetto(Integer codiceoggetto) {

	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	detachedCriteria.add(Restrictions.eq("codiceoggetto", codiceoggetto));
	return getHibernateTemplate().findByCriteria(detachedCriteria);
    }
}
