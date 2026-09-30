package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ModelliDAO;
import it.gruppoinit.pal.gp.core.domain.Modelli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class ModelliDAOImpl extends BaseDAOImpl<Modelli, PkId> implements ModelliDAO {

    @Override
    public Class<Modelli> getEntityClass() {

	return Modelli.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Modelli> findByFilter(Set<Software> softwareList) {

	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	if (softwareList.size() != 0) {
	    detachedCriteria.add(Restrictions.in("software", softwareList));
	}
	detachedCriteria.addOrder(Order.asc("ordine"));
	return getHibernateTemplate().findByCriteria(detachedCriteria);
    }
}
