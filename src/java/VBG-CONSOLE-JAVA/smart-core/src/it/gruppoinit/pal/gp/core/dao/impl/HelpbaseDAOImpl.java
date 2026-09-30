package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.HelpbaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Helpbase;
import it.gruppoinit.pal.gp.core.domain.HelpbaseId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class HelpbaseDAOImpl extends BaseDAOImpl<Helpbase, HelpbaseId> implements HelpbaseDAO {

    @Override
    public Class<Helpbase> getEntityClass() {

	return Helpbase.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Helpbase> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("id.software", ORMHelper.getSoftware()));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Helpbase findByContentAndSoftwares(String contentType, String software) {

	Helpbase helpbase = null;
	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("id.contenttype", contentType));
	if (software == null) {
	    det.add(Restrictions.in("id.software", new Object[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }));
	} else {
	    det.add(Restrictions.eq("id.software", software));
	}
	det.add(Restrictions.eq("id.tab", 0));
	List<Helpbase> list = getHibernateTemplate().findByCriteria(det);
	if (list != null && list.size() > 0) {
	    if (list.size() == 1) {
		helpbase = list.get(0);
	    } else {
		helpbase = (list.get(0).getId().getSoftware().equals(ORMHelper.getSoftware())) ? list.get(0) : list.get(1);
	    }
	}
	return helpbase;
    }
}
