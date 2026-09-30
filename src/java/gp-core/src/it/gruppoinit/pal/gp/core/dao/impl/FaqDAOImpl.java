package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FaqDAO;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class FaqDAOImpl extends BaseDAOImpl<Faq, PkId> implements FaqDAO {

    @Override
    public Class<Faq> getEntityClass() {

	return Faq.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Faq> findByFilter(List<String> softwareList, Integer firstResult, Integer maxResult, Boolean pubblicare) {

	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	if (softwareList.size() != 0) {
	    String[] codiciSoftware = new String[softwareList.size()];
	    detachedCriteria.add(Restrictions.in("software.codice", softwareList.toArray(codiciSoftware)));
	}
	if (pubblicare != null) {
	    detachedCriteria.add(Restrictions.eq("pubblicare", pubblicare));
	}
	if (null != firstResult && null != maxResult) {
	    return getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResult);
	} else {
	    return getHibernateTemplate().findByCriteria(detachedCriteria);
	}
    }
}
