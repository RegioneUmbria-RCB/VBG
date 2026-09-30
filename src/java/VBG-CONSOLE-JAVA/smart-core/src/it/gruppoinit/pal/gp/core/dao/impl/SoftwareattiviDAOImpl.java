package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.SoftwareattiviDAO;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.SoftwareattiviId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class SoftwareattiviDAOImpl extends BaseDAOImpl<Softwareattivi, SoftwareattiviId> implements SoftwareattiviDAO {

    @Override
    public Class<Softwareattivi> getEntityClass() {

	return Softwareattivi.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Softwareattivi> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.createAlias("software", "_software");
	crit.addOrder(Order.asc("_software.ordine"));
	if (firstResult != null && maxResult != null) {
	    return (List<Softwareattivi>) getHibernateTemplate().findByCriteria(crit, firstResult, maxResult);
	} else {
	    return (List<Softwareattivi>) getHibernateTemplate().findByCriteria(crit);
	}
    }
}
