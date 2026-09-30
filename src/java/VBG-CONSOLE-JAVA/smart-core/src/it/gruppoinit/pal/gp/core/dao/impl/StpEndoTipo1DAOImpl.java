package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.StpEndoTipo1DAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class StpEndoTipo1DAOImpl extends BaseDAOImpl<StpEndoTipo1, PkId> implements StpEndoTipo1DAO {

    @Override
    public Class<StpEndoTipo1> getEntityClass() {

	return StpEndoTipo1.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StpEndoTipo1 findByInventarioProcedimenti(String idcomune, Integer codiceinventario) {

	DetachedCriteria criteria = getIdcomuneCriteria(idcomune);
	DetachedCriteria inventarioCrit = criteria.createCriteria("inventarioprocedimenti");
	inventarioCrit.add(Restrictions.eq("id.codice", codiceinventario));
	List<StpEndoTipo1> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StpEndoTipo1 findbyStpCodice(Integer stpCodice) {

	DetachedCriteria criteria = getIdcomuneCriteria(ORMHelper.getIdcomunebase());
	criteria.add(Restrictions.eq("codiceStp", stpCodice));
	List<StpEndoTipo1> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StpEndoTipo1> findbySoftware(String software) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	DetachedCriteria inventarioCrit = criteria.createCriteria("inventarioprocedimenti");
	inventarioCrit.add(Restrictions.eq("software.codice", software));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StpEndoTipo1> verificaSchedeEndo1() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	DetachedCriteria inventarioCrit = criteria.createCriteria("inventarioprocedimenti");
	inventarioCrit.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	DetachedCriteria oggettiCrit = criteria.createCriteria("oggetti", Criteria.LEFT_JOIN);
	oggettiCrit.add(Restrictions.isNull("id.codice"));
	DetachedCriteria ordTipiEndo = inventarioCrit.createCriteria("tipoendo", Criteria.LEFT_JOIN);
	DetachedCriteria ordTipifamiglieEndo = ordTipiEndo.createCriteria("tipifamiglieendo", Criteria.LEFT_JOIN);
	ordTipifamiglieEndo.addOrder(Order.asc("tipo"));
	ordTipiEndo.addOrder(Order.asc("tipo"));
	inventarioCrit.addOrder(Order.asc("procedimento"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
