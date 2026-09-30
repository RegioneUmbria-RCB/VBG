package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RicercheDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Ricerche;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class RicercheDAOImpl extends BaseDAOImpl<Ricerche, PkId> implements RicercheDAO {

    @Override
    public Class<Ricerche> getEntityClass() {

	return Ricerche.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ricerche> findByFilter(Integer codiceresponsabile, String chiave) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	//criteria.add(Restrictions.or(Restrictions.eq("codiceresponsabile", codiceresponsabile), Restrictions.eq("flagGlobale", true)));
	criteria.add(Restrictions.or(Restrictions.eq("codiceresponsabile", codiceresponsabile), Restrictions.eq("flagGlobale", true)));
	criteria.add(Restrictions.eq("chiave", chiave));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
