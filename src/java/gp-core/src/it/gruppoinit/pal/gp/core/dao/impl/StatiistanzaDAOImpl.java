package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.StatiistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;

@Repository
public class StatiistanzaDAOImpl extends BaseDAOImpl<Statiistanza, StatiistanzaId> implements StatiistanzaDAO {

    @Override
    public Class<Statiistanza> getEntityClass() {

	return Statiistanza.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Statiistanza> findBySoftware(String software) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.software", software));
	det.addOrder(Order.asc("ordine"));
	det.addOrder(Order.asc("stato"));
	List<Statiistanza> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @Override
    public List<Statiistanza> findByStatocomportamentoChiuse() {

	return this.findByStatocomportamentoChiuse(false);
    }

    @Override
    public List<Statiistanza> findByStatocomportamentoAperte() {

	return this.findByStatocomportamentoAperte(false);
    }

    @Override
    public List<Statiistanza> findByStatocomportamentoChiuse(boolean tuttiSoftware) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (tuttiSoftware == false) {
	    criteria.add(Restrictions.eq("id.software", ORMHelper.getSoftware()));
	}
	DetachedCriteria statiComportamentoCrit = criteria.createCriteria("staticomportamento");
	statiComportamentoCrit.add(Restrictions.in("codcomportamento", new Integer[] { 1, -1 }));
	criteria.addOrder(Order.asc("ordine"));
	criteria.addOrder(Order.asc("stato"));
	List<Statiistanza> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<Statiistanza> findByStatocomportamentoAperte(boolean tuttiSoftware) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (tuttiSoftware == false) {
	    criteria.add(Restrictions.eq("id.software", ORMHelper.getSoftware()));
	}
	DetachedCriteria statiComportamentoCrit = criteria.createCriteria("staticomportamento");
	statiComportamentoCrit.add(Restrictions.eq("codcomportamento", 0));
	criteria.addOrder(Order.asc("ordine"));
	criteria.addOrder(Order.asc("stato"));
	List<Statiistanza> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<Statiistanza> findByStatocomportamentoChiuseNegativamente() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("id.software", ORMHelper.getSoftware()));
	DetachedCriteria statiComportamentoCrit = criteria.createCriteria("staticomportamento");
	statiComportamentoCrit.add(Restrictions.eq("codcomportamento", -1));
	criteria.addOrder(Order.asc("ordine"));
	criteria.addOrder(Order.asc("stato"));
	List<Statiistanza> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<Statiistanza> findByStatocomportamentoChiusePositivamente() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("id.software", ORMHelper.getSoftware()));
	DetachedCriteria statiComportamentoCrit = criteria.createCriteria("staticomportamento");
	statiComportamentoCrit.add(Restrictions.eq("codcomportamento", 1));
	criteria.addOrder(Order.asc("ordine"));
	criteria.addOrder(Order.asc("stato"));
	List<Statiistanza> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<Statiistanza> findStatiInWarning() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("id.software", ORMHelper.getSoftware()));
	criteria.add(Restrictions.eq("flagWarning", Boolean.TRUE));
	criteria.addOrder(Order.asc("ordine"));
	criteria.addOrder(Order.asc("stato"));
	List<Statiistanza> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<Statiistanza> findStati(String software) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("id.software", ORMHelper.getSoftware()));
	criteria.add(Restrictions.eq("flagFiltrabileFront", Boolean.TRUE));
	criteria.addOrder(Order.asc("ordine"));
	criteria.addOrder(Order.asc("stato"));
	List<Statiistanza> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }
}
