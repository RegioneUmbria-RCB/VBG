package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AmministrazioniruoliDAO;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniruoli;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniruoliId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AmministrazioniruoliDAOImpl extends BaseDAOImpl<Amministrazioniruoli, AmministrazioniruoliId> implements AmministrazioniruoliDAO {

    @Override
    public Class<Amministrazioniruoli> getEntityClass() {

	return Amministrazioniruoli.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Amministrazioniruoli> findByAmministrazione(Amministrazioni amministrazione) {

	List<Amministrazioniruoli> list = getHibernateTemplate().findByCriteria(criteriAmministrazione(amministrazione));
	return list;
    }

    private DetachedCriteria criteriAmministrazione(Amministrazioni amministrazioni) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("amministrazioni", amministrazioni));
	return criteria;
    }
}
