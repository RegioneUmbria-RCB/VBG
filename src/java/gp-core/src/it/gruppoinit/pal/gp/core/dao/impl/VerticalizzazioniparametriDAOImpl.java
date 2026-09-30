package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.dao.ComuniassociatiDAO;
import it.gruppoinit.pal.gp.core.dao.SoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniparametriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

@Repository
public class VerticalizzazioniparametriDAOImpl extends BaseDAOImpl<Verticalizzazioniparametri, PkId> implements VerticalizzazioniparametriDAO {

    private SoftwareDAO softwareDAO;
    private ComuniDAO comuniDAO;
    private ComuniassociatiDAO comuniassociatiDAO;

    @Autowired
    public void setComuniassociatiDAO(ComuniassociatiDAO comuniassociatiDAO) {

	this.comuniassociatiDAO = comuniassociatiDAO;
    }

    @Autowired
    public void setComuniDAO(ComuniDAO comuniDAO) {

	this.comuniDAO = comuniDAO;
    }

    @Autowired
    public void setSoftwareDAO(SoftwareDAO softwareDAO) {

	this.softwareDAO = softwareDAO;
    }

    @Override
    public Class<Verticalizzazioniparametri> getEntityClass() {

	return Verticalizzazioniparametri.class;
    }

    @Override
    public List<Verticalizzazioniparametri> findByModuloAndIdcomuneAndSoftware(String modulo, String idcomune, String software) {

	return this.findByModuloAndIdcomuneAndSoftwareAndComune(modulo, idcomune, software, null);
    }

    @Override
    public List<Verticalizzazioniparametri> findByModuloAndIdcomuneAndSoftwareAndComune(String modulo, String idcomune, String software,
	    String codiceComune) {

	DetachedCriteria criteria = getIdcomuneCriteria();
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

    @SuppressWarnings("unchecked")
    @Override
    public List<ChiaveValoreBean<Comuni, Software>> findComuniESoftware(String modulo, String... parametro) {

	List<Comuniassociati> comunis = comuniassociatiDAO.findByIdcomune(ORMHelper.getIdcomune());
	// la lista serve per Non far tornare il valore codicecomune vuoto
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "SELECT software, codicecomune FROM " + schemaName + ".verticalizzazioniparametri WHERE idcomune=? AND modulo=?" +
		     " and S@FTWARE and PARAMS group by software, codicecomune";
	String sqlParams = " parametro in (";
	Object[] qm = new Object[parametro.length];
	for (int i = 0; i < qm.length; i++) {
	    qm[i] = "?";
	}
	sqlParams += StringUtils.join(qm, ",");
	sqlParams += ")";
	sql = sql.replaceAll("PARAMS", sqlParams);
	String sql1 = sql.replace("S@FTWARE", "software<>?");
	SQLQuery q = getSession().createSQLQuery(sql1);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, modulo);
	q.setString(2, "TT");
	int pos = 3;
	for (String p : parametro) {
	    q.setString(pos, p);
	    pos++;
	}
	List<Object[]> result = q.list();
	List<ChiaveValoreBean<Comuni, Software>> res = new ArrayList<ChiaveValoreBean<Comuni, Software>>();
	if (!result.isEmpty()) {
	    for (Object[] objects : result) {
		String software = (String) objects[0];
		String codicecomune = (String) objects[1];
		Software s = softwareDAO.findById(software);
		if (StringUtils.isBlank(codicecomune)) {
		    for (Comuniassociati comuniassociati : comunis) {
			Comuni c = comuniDAO.findById(comuniassociati.getId().getCodicecomune());
			ChiaveValoreBean<Comuni, Software> cvb = new ChiaveValoreBean<Comuni, Software>();
			cvb.setChiave(c);
			cvb.setValore(s);
			res.add(cvb);
		    }
		} else {
		    Comuni c = comuniDAO.findById(codicecomune);
		    ChiaveValoreBean<Comuni, Software> cvb = new ChiaveValoreBean<Comuni, Software>();
		    cvb.setChiave(c);
		    cvb.setValore(s);
		    res.add(cvb);
		}
	    }
	    return res;
	}
	String sql2 = sql.replace("S@FTWARE", "software=?");
	q = getSession().createSQLQuery(sql2);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, modulo);
	q.setString(2, "TT");
	pos = 3;
	for (String p : parametro) {
	    q.setString(pos, p);
	    pos++;
	}
	result = q.list();
	if (!result.isEmpty()) {
	    for (Object[] objects : result) {
		String software = (String) objects[0];
		String codicecomune = (String) objects[1];
		Software s = softwareDAO.findById(software);
		if (StringUtils.isBlank(codicecomune)) {
		    for (Comuniassociati comuniassociati : comunis) {
			Comuni c = comuniDAO.findById(comuniassociati.getId().getCodicecomune());
			ChiaveValoreBean<Comuni, Software> cvb = new ChiaveValoreBean<Comuni, Software>();
			cvb.setChiave(c);
			cvb.setValore(s);
			res.add(cvb);
		    }
		} else {
		    Comuni c = comuniDAO.findById(codicecomune);
		    ChiaveValoreBean<Comuni, Software> cvb = new ChiaveValoreBean<Comuni, Software>();
		    cvb.setChiave(c);
		    cvb.setValore(s);
		    res.add(cvb);
		}
	    }
	    return res;
	}
	return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Verticalizzazioniparametri> findByCriteria(DetachedCriteria criteria) {

	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Verticalizzazioniparametri> findParametriByModulo(String modulo, String... parametro) {

	DetachedCriteria criteria = getEmptyCriteriaForClass();
	criteria.add(Restrictions.eq("verticalizzazioniparametribase.id.modulo", modulo));
	criteria.add(Restrictions.in("verticalizzazioniparametribase.id.parametro", parametro));
	criteria.addOrder(Order.asc("id.idcomune"));
	criteria.addOrder(Order.asc("verticalizzazioniparametribase.id.parametro"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
