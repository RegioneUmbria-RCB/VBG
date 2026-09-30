package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.LayouttestiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAORestrictionMode;
import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.domain.LayouttestiId;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LayouttestiDAOImpl extends BaseDAOImpl<Layouttesti, LayouttestiId> implements LayouttestiDAO {

    @Override
    public Class<Layouttesti> getEntityClass() {

	return Layouttesti.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String resolveCode(String code, String software) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	Criterion criterion = getCriterionForObjects(new String[] { "id.software", "id.software" }, new Object[] { WebConstants.SOFTWARE_TT,
		software }, new MatchMode[] { MatchMode.EXACT, MatchMode.EXACT }, DAORestrictionMode.OR);
	criteria.add(criterion);
	criteria.add(Restrictions.eq("id.codicetesto", code));
	List<Layouttesti> list = getHibernateTemplate().findByCriteria(criteria);
	String testo = null;
	switch (list.size()) {
	case 0:
	    break;
	case 1:
	    testo = list.get(0).getNuovotesto();
	    break;
	default:
	    for (Layouttesti layouttesti : list) {
		if (!layouttesti.getId().getSoftware().equals(WebConstants.SOFTWARE_TT)) {
		    testo = layouttesti.getNuovotesto();
		    break;
		}
	    }
	}
	return testo;
    }
}
