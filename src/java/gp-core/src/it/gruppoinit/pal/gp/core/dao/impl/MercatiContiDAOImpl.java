package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiContiDAO;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MercatiContiDAOImpl extends BaseDAOImpl<MercatiConti, PkId> implements MercatiContiDAO {

    @Override
    public Class<MercatiConti> getEntityClass() {

	return MercatiConti.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiConti> findByMercati(MercatiConti entity) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("mercati", entity.getMercati()));
	det.createAlias("conti", "_conti");
	det.addOrder(Order.asc("anno"));
	det.addOrder(Order.asc("_conti.descrizione"));
	return (List<MercatiConti>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiConti> findByMercatiAndAnno(Mercati entity, Integer anno) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("mercatiId", entity.getId().getCodice()));
	det.add(Restrictions.eq("anno", anno));
	return (List<MercatiConti>) getHibernateTemplate().findByCriteria(det);
    }
}
