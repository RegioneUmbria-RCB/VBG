package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAORestrictionMode;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Layouttestibase;
import it.gruppoinit.pal.gp.core.domain.LayouttestibaseId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LayouttestibaseDAOImpl extends BaseDAOImpl<Layouttestibase, LayouttestibaseId> implements LayouttestibaseDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Layouttestibase> findAll(Integer firstResult, Integer maxResult) {

	if (null != firstResult && null != maxResult) {
	    return (List<Layouttestibase>) getHibernateTemplate().findByExample(new Layouttestibase(), firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Layouttestibase>) getHibernateTemplate().findByExample(new Layouttestibase());
	}
    }

    @Override
    public Class<Layouttestibase> getEntityClass() {

	return Layouttestibase.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String resolveCode(String code, String software) {

	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
	Criterion criterion = getCriterionForObjects(new String[] { "id.software", "id.software" },
		new Object[] { WebConstants.SOFTWARE_TT, software }, new MatchMode[] { MatchMode.EXACT, MatchMode.EXACT }, DAORestrictionMode.OR);
	criteria.add(criterion);
	criteria.add(Restrictions.eq("id.codicetesto", code));
	List<Layouttestibase> list = getHibernateTemplate().findByCriteria(criteria);
	String testo = code;
	switch (list.size()) {
	case 0:
	    break;
	case 1:
	    testo = list.get(0).getTesto();
	    break;
	default:
	    for (Layouttestibase layouttestibase : list) {
		if (!layouttestibase.getId().getSoftware().equals(WebConstants.SOFTWARE_TT)) {
		    testo = layouttestibase.getTesto();
		    break;
		}
	    }
	}
	return testo;
    }

    @Override
    public List<Layouttestibase> findByPrefissoOrderBySoftware(String prefissoEtichette) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("id.software", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, String.class));
	ft.addRestriction(fr);
	fr.addFilterField(FilterUtils.like("id.codicetesto", prefissoEtichette + "%"));
	ft.addOrder(FilterUtils.orderAsc("id.codicetesto"));
	ft.addOrder(FilterUtils.orderAsc("moduloopzionale", "software"));
	return findByFilterTable(ft);
    }
}
