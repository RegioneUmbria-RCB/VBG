package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import java.util.Collections;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAORestrictionMode;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.domain.LayouttestiId;
import it.gruppoinit.pal.gp.core.domain.Layouttestibase;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

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
	Criterion criterion = getCriterionForObjects(new String[] { "id.software", "id.software" },
		new Object[] { WebConstants.SOFTWARE_TT, software }, new MatchMode[] { MatchMode.EXACT, MatchMode.EXACT }, DAORestrictionMode.OR);
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

    @Override
    public List<Layouttesti> findByPrefissoOrderBySoftware(String prefissoEtichette) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("id.software", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, String.class));
	ft.addRestriction(fr);
	fr.addFilterField(FilterUtils.like("id.codicetesto", prefissoEtichette + "%"));
	ft.addOrder(FilterUtils.orderAsc("id.codicetesto"));
	ft.addOrder(FilterUtils.orderAsc("moduloopzionale", "software"));
	return findByFilterTable(ft);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LayoutTestiDTO> findTesti() {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryElencoTesti queryHelper = new QueryElencoTesti(sessimpl);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Layouttesti.class).addSynchronizedEntityClass(Layouttestibase.class);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(LayoutTestiDTO.class));
	List<LayoutTestiDTO> lista = q.list();
	Collections.sort(lista, new LayoutTestiDTOComparator());
	return lista;
    }
}
