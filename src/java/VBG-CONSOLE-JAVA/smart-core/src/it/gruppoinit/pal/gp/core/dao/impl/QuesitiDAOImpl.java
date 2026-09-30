package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.QuesitiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Quesiti;
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
public class QuesitiDAOImpl extends BaseDAOImpl<Quesiti, PkId> implements QuesitiDAO {

    @Override
    public Class<Quesiti> getEntityClass() {

	return Quesiti.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Quesiti> findByFilter(Set<Software> softwareList) {

	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	if (softwareList.size() != 0) {
	    detachedCriteria.add(Restrictions.in("software", softwareList));
	}
	detachedCriteria.addOrder(Order.desc("data"));
	return getHibernateTemplate().findByCriteria(detachedCriteria);
    }
}
