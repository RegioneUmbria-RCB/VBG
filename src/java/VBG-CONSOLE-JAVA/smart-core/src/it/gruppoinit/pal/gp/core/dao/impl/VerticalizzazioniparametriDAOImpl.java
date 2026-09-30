package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniparametriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
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
public class VerticalizzazioniparametriDAOImpl extends BaseDAOImpl<Verticalizzazioniparametri, PkId> implements VerticalizzazioniparametriDAO {

    @Override
    public Class<Verticalizzazioniparametri> getEntityClass() {

	return Verticalizzazioniparametri.class;
    }

    @Override
    public Verticalizzazioniparametri findByModuloAndParametro(String modulo, String parametro) {

	return this.findByModuloAndParametroAndSoftware(modulo, parametro, ORMHelper.getSoftware());
    }

    @Override
    public Verticalizzazioniparametri findByModuloAndParametroAndComune(String modulo, String parametro, String codiceComune) {

	return this.findByModuloAndParametroAndSoftwareAndComune(modulo, parametro, ORMHelper.getSoftware(), codiceComune);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Verticalizzazioniparametri findByModuloAndParametroAndSoftware(String modulo, String parametro, String software) {

	return findByModuloAndParametroAndSoftwareAndComune(modulo, parametro, software, null);
    }

    @Override
    public Verticalizzazioniparametri findByModuloAndParametroAndSoftwareAndComune(String modulo, String parametro, String software,
	    String codiceComune) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (StringUtils.isBlank(software)) {
	    det.add(Restrictions.in("software.codice", new Object[] { "TT", ORMHelper.getSoftware() }));
	} else {
	    det.add(Restrictions.in("software.codice", new Object[] { "TT", software }));
	}
	det.add(Restrictions.eq("verticalizzazioniparametribase.id.modulo", modulo));
	det.add(Restrictions.eq("verticalizzazioniparametribase.id.parametro", parametro));
	//////
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
	//////
	List<Verticalizzazioniparametri> list = getHibernateTemplate().findByCriteria(det);
	if (list != null && list.size() > 0) {
	    if (list.size() == 1) {
		return list.get(0);
	    } else {
		String key = codiceComune + "-" + software;
		String keyTT = codiceComune + "-" + WebConstants.SOFTWARE_TT;
		String keyTTSoft = "TUTTI" + "-" + software;
		String keyTTeTT = "TUTTI" + "-" + WebConstants.SOFTWARE_TT;
		Map<String, Verticalizzazioniparametri> m = new HashMap<String, Verticalizzazioniparametri>();
		for (Verticalizzazioniparametri v : list) {
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

    @Override
    public List<Verticalizzazioniparametri> findByModuloAndIdcomuneAndSoftware(String modulo, String idcomune, String software) {

	return this.findByModuloAndIdcomuneAndSoftwareAndComune(modulo, idcomune, software, null);
    }

    @Override
    public List<Verticalizzazioniparametri> findByModuloAndIdcomuneAndSoftwareAndComune(String modulo, String idcomune, String software,
	    String codiceComune) {

	DetachedCriteria criteria = getIdcomuneCriteria(idcomune);
	criteria.add(Restrictions.eq("verticalizzazioniparametribase.id.modulo", modulo));
	criteria.createAlias("software", "_software");
	criteria.add(Restrictions.in("_software.codice", new String[] { software, WebConstants.SOFTWARE_TT }));
	//////
	criteria.createAlias("comune", "_comune", DetachedCriteria.LEFT_JOIN);
	if (StringUtils.isBlank(codiceComune)) {
	    criteria.add(Restrictions.isNull("_comune.codicecomune"));
	} else {
	    Criterion cnull = Restrictions.isNull("_comune.codicecomune");
	    Criterion comune = Restrictions.eq("_comune.codicecomune", codiceComune);
	    LogicalExpression orExp = Restrictions.or(cnull, comune);
	    criteria.add(orExp);
	}
	//////
	criteria.addOrder(OrderBySqlFormula.asc("_comune.comune", FunctionsEnum.NVL_FUNCTION, "'AAAAAAAAAAAA'")); // IMPORTANTE PER METTERE PRIMA QUELLI CON COMNE NULLP
	// IN MODO CHE SE PRESENTE UN PARAMETRO PER COMUNE ALLORA LO SOVRASCRIVERA'
	criteria.addOrder(Order.asc("_software.moduloopzionale"));
	criteria.addOrder(Order.asc("verticalizzazioniparametribase.id.parametro"));
	@SuppressWarnings("unchecked")
	List<Verticalizzazioniparametri> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }
}
