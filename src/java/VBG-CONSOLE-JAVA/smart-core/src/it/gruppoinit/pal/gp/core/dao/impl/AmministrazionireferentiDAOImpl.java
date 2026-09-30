package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AmministrazionireferentiDAO;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AmministrazionireferentiDAOImpl extends BaseDAOImpl<Amministrazionireferenti, PkId> implements AmministrazionireferentiDAO {

    @Override
    public Class<Amministrazionireferenti> getEntityClass() {

	return Amministrazionireferenti.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Amministrazionireferenti> findByAmministrazioni(Amministrazioni amministrazioni) {

	List<Amministrazionireferenti> list = getHibernateTemplate().findByCriteria(criteriaAmministrazione(amministrazioni));
	return list;
    }

    private DetachedCriteria criteriaAmministrazione(Amministrazioni amministrazioni) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (EntityUtils.getNestedProperty(amministrazioni, "id.codice") != null) {
	    criteria.add(Restrictions.eq("amministrazioni.id", amministrazioni.getId()));
	}
	criteria.createAlias("amministrazioni", "_amministrazioni");
	criteria.addOrder(Order.desc("_amministrazioni.amministrazione"));
	return criteria;
    }
}
