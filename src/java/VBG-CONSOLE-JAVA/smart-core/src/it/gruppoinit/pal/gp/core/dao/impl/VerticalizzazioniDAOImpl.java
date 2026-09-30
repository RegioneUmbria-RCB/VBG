package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VerticalizzazioniDAOImpl extends BaseDAOImpl<Verticalizzazioni, PkId> implements VerticalizzazioniDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<Verticalizzazioni> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public Class<Verticalizzazioni> getEntityClass() {

	return Verticalizzazioni.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Verticalizzazioni findByModuloEComuneESoftware(String modulo, String codiceComune, String software) {

	// Verticalizzazioni verticalizzazioni = null;
	DetachedCriteria det = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(software)) {
	    det.add(Restrictions.in("software.codice", new Object[] { "TT", software }));
	} else {
	    det.add(Restrictions.in("software.codice", new Object[] { "TT", ORMHelper.getSoftware() }));
	}
	det.add(Restrictions.eq("verticalizzazionibase.modulo", modulo));
	det.createAlias("comune", "_comune", DetachedCriteria.LEFT_JOIN);
	if (StringUtils.isBlank(codiceComune)) {
	    det.add(Restrictions.isNull("_comune.codicecomune"));
	} else {
	    Criterion cnull = Restrictions.isNull("_comune.codicecomune");
	    Criterion comune = Restrictions.eq("_comune.codicecomune", codiceComune);
	    LogicalExpression orExp = Restrictions.or(cnull, comune);
	    det.add(orExp);
	}
	codiceComune = StringUtils.defaultString(codiceComune, "TUTTI");
	software = StringUtils.defaultString(software, WebConstants.SOFTWARE_TT);
	List<Verticalizzazioni> list = getHibernateTemplate().findByCriteria(det);
	if (list != null && list.size() > 0) {
	    if (list.size() == 1) {
		return list.get(0);
	    } else {
		String key = codiceComune + "-" + software;
		String keyTT = codiceComune + "-" + WebConstants.SOFTWARE_TT;
		String keyTTSoft = "TUTTI" + "-" + software;
		String keyTTeTT = "TUTTI" + "-" + WebConstants.SOFTWARE_TT;
		Map<String, Verticalizzazioni> m = new HashMap<String, Verticalizzazioni>();
		for (Verticalizzazioni v : list) {
		    String kloc = (v.getComune() == null ? "TUTTI" : v.getComune().getCodicecomune()) + "-" + v.getSoftware().getCodice();
		    m.put(kloc, v);
		}
		if (m.get(key) != null) {
		    return m.get(key);
		} else {
		    if (m.get(keyTT) != null) {
			return m.get(keyTT);
		    }
		    if (m.get(keyTTSoft) != null) {
			return m.get(keyTTSoft);
		    }
		    return m.get(keyTTeTT);
		}
	    }
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Verticalizzazioni> findByVerticalizzazionibase(Verticalizzazionibase verticalizzazionibase) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// criteria.createAlias("verticalizzazionibase", "_verticalizzazionibase");
	criteria.add(Restrictions.eq("verticalizzazionibase", verticalizzazionibase));
	criteria.createAlias("software", "_software");
	criteria.createAlias("comune", "_comune", DetachedCriteria.LEFT_JOIN);
	criteria.addOrder(OrderBySqlFormula.asc("_comune.comune", FunctionsEnum.NVL_FUNCTION, "'AAAAAAAAAAAAAAAA'"));
	criteria.addOrder(Order.asc("_software.ordine"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
