package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.VwPosteggiconcessioniDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioni;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioniId;

@Repository
public class VwPosteggiconcessioniDAOImpl extends BaseDAOImpl<VwPosteggiconcessioni, VwPosteggiconcessioniId> implements VwPosteggiconcessioniDAO {

    @Override
    public void delete(VwPosteggiconcessioni entity) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(VwPosteggiconcessioni entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwPosteggiconcessioni entity) {

	throw new NotImplementedException();
    }

    @Override
    public Class<VwPosteggiconcessioni> getEntityClass() {

	return VwPosteggiconcessioni.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwPosteggiconcessioni> findPosteggiMercatoUso(Integer codiceMercato, Integer idUso, Integer firstResult, Integer maxResults) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("id.codicemercato", codiceMercato));
	crit.add(Restrictions.eq("id.iduso", idUso));
	crit.createAlias("posteggio", "_posteggio");
	Criterion lhs = Restrictions.isNull("_posteggio.disabilitato");
	Criterion rhs = Restrictions.eq("_posteggio.disabilitato", Boolean.FALSE);
	Criterion posDisabilitato = Restrictions.or(lhs, rhs);
	crit.add(posDisabilitato);
	crit.addOrder(Order.asc("_posteggio.codiceposteggio"));
	List<VwPosteggiconcessioni> list = null;
	if (firstResult != null && maxResults != null) {
	    list = (List<VwPosteggiconcessioni>) getHibernateTemplate().findByCriteria(crit, firstResult, maxResults);
	} else {
	    list = (List<VwPosteggiconcessioni>) getHibernateTemplate().findByCriteria(crit);
	}
	return list;
    }

    @Override
    public int countPosteggiMercatoUso(Integer codiceMercato, Integer idUso) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("id.codicemercato", codiceMercato));
	crit.add(Restrictions.eq("id.iduso", idUso));
	crit.createAlias("posteggio", "_posteggio");
	Criterion lhs = Restrictions.isNull("_posteggio.disabilitato");
	Criterion rhs = Restrictions.eq("_posteggio.disabilitato", Boolean.FALSE);
	Criterion posDisabilitato = Restrictions.or(lhs, rhs);
	crit.add(posDisabilitato);
	crit.setProjection(Projections.rowCount());
	int ris = ((Integer) getHibernateTemplate().findByCriteria(crit).get(0)).intValue();
	return ris;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwPosteggiconcessioni> findByMercatoUsoAndPosteggio(Integer codiceMercato, Integer idUso, Integer idPosteggio, Integer firstResult,
	    Integer maxResults) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("id.codicemercato", codiceMercato));
	crit.add(Restrictions.eq("id.iduso", idUso));
	crit.createAlias("posteggio", "_posteggio");
	crit.add(Restrictions.eq("_posteggio.id.codice", idPosteggio));
	Criterion lhs = Restrictions.isNull("_posteggio.disabilitato");
	Criterion rhs = Restrictions.eq("_posteggio.disabilitato", Boolean.FALSE);
	Criterion posDisabilitato = Restrictions.or(lhs, rhs);
	crit.add(posDisabilitato);
	crit.addOrder(Order.asc("_posteggio.codiceposteggio"));
	List<VwPosteggiconcessioni> list = null;
	if (firstResult != null && maxResults != null) {
	    list = (List<VwPosteggiconcessioni>) getHibernateTemplate().findByCriteria(crit, firstResult, maxResults);
	} else {
	    list = (List<VwPosteggiconcessioni>) getHibernateTemplate().findByCriteria(crit);
	}
	return list;
    }
}
