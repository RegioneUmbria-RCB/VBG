package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VerticalizzazionibaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class VerticalizzazionibaseDAOImpl extends BaseDAOImpl<Verticalizzazionibase, String> implements VerticalizzazionibaseDAO {

    @Override
    public Class<Verticalizzazionibase> getEntityClass() {

	return Verticalizzazionibase.class;
    }

    @Override
    public List<Verticalizzazionibase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "modulo", DAOOrderTypeEnum.ASC);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isConfigurataPerComuneAndSoftware(Verticalizzazionibase verticalizzazionibase) {

	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
	DetachedCriteria verticalizzazioniCrit = criteria.createCriteria("verticalizzazionis", "_verticalizzazioni", Criteria.LEFT_JOIN);
	verticalizzazioniCrit.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	verticalizzazioniCrit.add(Restrictions.eq("attivo", 1));
	criteria.add(Restrictions.eq("modulo", verticalizzazionibase.getModulo()));
	List<Verticalizzazionibase> list = getHibernateTemplate().findByCriteria(criteria);
	return !list.isEmpty();
    }
}
