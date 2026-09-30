package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AmministrazioniresponsabiliDAO;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniresponsabili;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AmministrazioniresponsabiliDAOImpl extends BaseDAOImpl<Amministrazioniresponsabili, PkId> implements AmministrazioniresponsabiliDAO {

    @Override
    public Class<Amministrazioniresponsabili> getEntityClass() {

	return Amministrazioniresponsabili.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Amministrazioniresponsabili> findByAmministrazione(Amministrazioni amministrazione) {

	List<Amministrazioniresponsabili> list = getHibernateTemplate().findByCriteria(criteriAmministrazione(amministrazione));
	return list;
    }

    private DetachedCriteria criteriAmministrazione(Amministrazioni amministrazioni) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("amministrazioni", amministrazioni));
	return criteria;
    }
}
