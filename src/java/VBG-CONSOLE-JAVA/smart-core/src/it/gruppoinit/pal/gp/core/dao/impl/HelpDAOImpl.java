package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.HelpDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Help;
import it.gruppoinit.pal.gp.core.domain.HelpId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class HelpDAOImpl extends BaseDAOImpl<Help, HelpId> implements HelpDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<Help> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public Class<Help> getEntityClass() {

	return Help.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Help findByContentAndSoftwares(String contentType, String software) {

	Help help = null;
	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("id.contenttype", contentType));
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	if (software == null) {
	    det.add(Restrictions.in("id.software", new Object[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }));
	} else {
	    det.add(Restrictions.eq("id.software", software));
	}
	det.add(Restrictions.eq("id.tab", 0));
	List<Help> list = getHibernateTemplate().findByCriteria(det);
	if (list != null && list.size() > 0) {
	    if (list.size() == 1) {
		help = list.get(0);
	    } else {
		help = (list.get(0).getId().getSoftware().equals(ORMHelper.getSoftware())) ? list.get(0) : list.get(1);
	    }
	}
	return help;
    }
}
