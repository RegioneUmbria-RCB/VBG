package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiUsoDAO;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MercatiUsoDAOImpl extends BaseDAOImpl<MercatiUso, PkId> implements MercatiUsoDAO {

    @Override
    public Class<MercatiUso> getEntityClass() {

	return MercatiUso.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiUso> findByMercato(Mercati mercati) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("mercati.id.codice", mercati.getId().getCodice()));
	det.addOrder(Order.desc("peso"));
	det.addOrder(Order.asc("descrizione"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
