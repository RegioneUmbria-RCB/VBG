package it.gruppoinit.pal.gp.core.features.decodifiche.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Decodifiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.decodifiche.dao.DecodificheDAO;

@Repository
public class DecodificheDAOImpl extends BaseDAOImpl<Decodifiche, PkId> implements DecodificheDAO {

    @Override
    public Class<Decodifiche> getEntityClass() {

	return Decodifiche.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Decodifiche> findByTabella(String tabella) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(tabella)) {
	    det.add(Restrictions.eq("tabella", tabella));
	}
	det.addOrder(Order.asc("ordine"));
	det.addOrder(Order.asc("valore"));
	return (List<Decodifiche>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> findDistinctTabelle() {

	DetachedCriteria det = getIdcomuneCriteria();
	ProjectionList pl = Projections.projectionList();
	det.addOrder(Order.asc("tabella"));
	pl.add(Projections.groupProperty("tabella"));
	det.setProjection(pl);
	return (List<String>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Decodifiche> findByTabellaAndRaggruppamento(String tabella, String raggruppamento) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("tabella", tabella));
	det.add(Restrictions.eq("raggruppamento", raggruppamento));
	det.addOrder(Order.asc("ordine"));
	det.addOrder(Order.asc("valore"));
	return (List<Decodifiche>) getHibernateTemplate().findByCriteria(det);
    }
}
