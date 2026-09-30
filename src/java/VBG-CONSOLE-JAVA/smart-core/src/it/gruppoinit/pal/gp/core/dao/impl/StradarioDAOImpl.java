package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.StradarioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

/**
 * @author Riccardo Bocci
 * @author gianpaolot
 * 
 */
@Repository
public class StradarioDAOImpl extends BaseComuniAssociatiDAOImpl<Stradario, PkId> implements StradarioDAO {

    @Override
    public Class<Stradario> getEntityClass() {

	return Stradario.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, String[] codiciComuniAbilitati, Integer firstResult,
	    Integer maxResult) {

	return this.findByDescrizione(descrizione, codiceComune, codiciComuniAbilitati, firstResult, maxResult, true);
    }

    @Override
    protected void setCodiceComune(Stradario entity) {

	if (!checkIfCodiceComuneIsSet(entity.getComune())) {
	    entity.setComune(getDefaultComune());
	}
    }

    @Override
    public List<Stradario> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Stradario> findAllByCodiciComuni(String[] codiciComune) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	DetachedCriteria comuneCriteria = criteria.createCriteria("comune", Criteria.LEFT_JOIN);
	if (null != codiciComune && codiciComune.length > 0) {
	    // devo mettere anche i valori null nella ricerca per non escludere quei record che non hanno
	    // associato alcun comune
	    comuneCriteria.add(Restrictions.or(Restrictions.in("codicecomune", codiciComune), Restrictions.isNull("codicecomune")));
	}
	comuneCriteria.addOrder(Order.asc("comune"));
	criteria.addOrder(Order.asc("descrizione"));
	return (List<Stradario>) getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Stradario> findByMercatoAndDescrizione(Integer codicemercato, String descrizione) {

	return this.findByMercatoAndDescrizione(codicemercato, descrizione, true);
    }

    @Override
    public List<Stradario> findByMercatoAndDescrizione(Integer codicemercato, String descrizione, boolean searchDisabilitati) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createCriteria("mercatistradarios", "mercatistradario");
	criteria.createAlias("mercatistradario.mercato", "_mercato");
	criteria.add(Restrictions.eq("_mercato.id.codice", codicemercato));
	criteria.createAlias("mercatistradario.stradario", "_stradario");
	criteria.add(Restrictions.ilike("_stradario.descrizione", descrizione, MatchMode.ANYWHERE));
	// Gestisce la ricerca degli stradari disabilitati
	if (!searchDisabilitati) {
	    criteria.add(Restrictions.isNull("_stradario.datavalidita"));
	}
	criteria.addOrder(Order.asc("_stradario.descrizione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<Stradario> findByDescrizioneEsatta(String descrizione, String codiceComune, String[] codiciComuniAbilitati, Integer firstResult,
	    Integer maxResult) {

	String concat = getConcatFunctionForDialect();
	String hql = "Select s from Stradario s left join s.comune c where s.id.idcomune=? ";
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		hql += " and upper(" + concat + concat + "s.prefisso,' '),s.descrizione)) = ?";
	    }
	}
	if (null != codiciComuniAbilitati && codiciComuniAbilitati.length > 0) {
	    // devo mettere anche i valori null nella ricerca per non escludere quei record che non hanno
	    // associato alcun comune per questo ho utilizzato la LEFT_JOIN	    
	    if (StringUtils.isNotBlank(codiceComune)) {
		hql += " and (c.codicecomune = ? or c.codicecomune is null)";
		//		comuneCriteria.add(Restrictions.or(Restrictions.in("codicecomune", new String[] { codiceComune }),
		//			Restrictions.isNull("codicecomune")));
	    } else {
		String questionMarks = "";
		if (null != codiciComuniAbilitati) {
		    for (String codicecomune2 : codiciComuniAbilitati) {
			questionMarks += "?,";
		    }
		    if (codiciComuniAbilitati != null && codiciComuniAbilitati.length > 0) {
			questionMarks = questionMarks.substring(0, questionMarks.length() - 1);
		    }
		    hql += " and (c.codicecomune in (" + questionMarks + ") or c.codicecomune is null)";
		}
	    }
	}
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setFirstResult(firstResult);
	q.setMaxResults(maxResult);
	int paramPos = 0;
	q.setString(paramPos, ORMHelper.getIdcomune());
	paramPos++;
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		q.setString(paramPos, descrizione.toUpperCase());
		paramPos++;
	    }
	}
	if (null != codiciComuniAbilitati && codiciComuniAbilitati.length > 0) {
	    // devo mettere anche i valori null nella ricerca per non escludere quei record che non hanno
	    // associato alcun comune per questo ho utilizzato la LEFT_JOIN	    
	    if (StringUtils.isNotBlank(codiceComune)) {
		q.setString(paramPos, codiceComune);
		paramPos++;
	    } else {
		if (null != codiciComuniAbilitati) {
		    for (String codicecomune2 : codiciComuniAbilitati) {
			q.setString(paramPos, codicecomune2);
			paramPos++;
		    }
		}
	    }
	}
	return (List<Stradario>) q.list();
    }

    private String getConcatFunctionForDialect() {

	String concatFunction = "concat(";
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	String hibernateDialect = dialetto.toString();
	if (hibernateDialect.indexOf("MySQL") > 0) {
	    concatFunction = "concat_ws('',";
	}
	return concatFunction;
    }

    @Override
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, String[] codiciComuniAbilitati, Integer firstResult,
	    Integer maxResult, boolean searchDisabilitati) {

	String concat = getConcatFunctionForDialect();
	String hql = "Select s from Stradario s left join s.comune c where s.id.idcomune=? ";
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		hql += " and ((upper(" + concat + concat + concat + concat
			+ "s.prefisso,' '),s.descrizione),' '),s.locfraz)) like ?) or (upper(s.codviario) like ?) )";
	    }
	}
	if (null != codiciComuniAbilitati && codiciComuniAbilitati.length > 0) {
	    // devo mettere anche i valori null nella ricerca per non escludere quei record che non hanno
	    // associato alcun comune per questo ho utilizzato la LEFT_JOIN	    
	    if (StringUtils.isNotBlank(codiceComune)) {
		hql += " and (c.codicecomune = ? or c.codicecomune is null)";
		//		comuneCriteria.add(Restrictions.or(Restrictions.in("codicecomune", new String[] { codiceComune }),
		//			Restrictions.isNull("codicecomune")));
	    } else {
		String questionMarks = "";
		if (null != codiciComuniAbilitati) {
		    for (String codicecomune : codiciComuniAbilitati) {
			questionMarks += "?,";
		    }
		    if (codiciComuniAbilitati != null && codiciComuniAbilitati.length > 0) {
			questionMarks = questionMarks.substring(0, questionMarks.length() - 1);
		    }
		    hql += " and (c.codicecomune in (" + questionMarks + ") or c.codicecomune is null)";
		}
	    }
	}
	// Gestisce la ricerca degli stradari disabilitati
	if (!searchDisabilitati) {
	    hql += " and datavalidita is null";
	}
	// Ordinamento per descrizione ASC
	hql += " ORDER BY lower(s.prefisso)ASC,lower(s.descrizione) ASC";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setFirstResult(firstResult);
	q.setMaxResults(maxResult);
	int paramPos = 0;
	q.setString(paramPos, ORMHelper.getIdcomune());
	paramPos++;
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		q.setString(paramPos, "%" + descrizione.toUpperCase() + "%");
		paramPos++;
		q.setString(paramPos, descrizione.toUpperCase() + "%");
		paramPos++;
	    }
	}
	if (null != codiciComuniAbilitati && codiciComuniAbilitati.length > 0) {
	    // devo mettere anche i valori null nella ricerca per non escludere quei record che non hanno
	    // associato alcun comune per questo ho utilizzato la LEFT_JOIN	    
	    if (StringUtils.isNotBlank(codiceComune)) {
		q.setString(paramPos, codiceComune);
		paramPos++;
	    } else {
		if (null != codiciComuniAbilitati) {
		    for (String codicecomune2 : codiciComuniAbilitati) {
			q.setString(paramPos, codicecomune2);
			paramPos++;
		    }
		}
	    }
	}
	return (List<Stradario>) q.list();
	//	DetachedCriteria det = getIdcomuneCriteria();
	//	if (StringUtils.isNotBlank(amministrazione)) {
	//	    try {
	//		det.add(Restrictions.eq("id.codice", Integer.parseInt(amministrazione)));
	//	    } catch (Exception e) {
	//		det.add(Restrictions.ilike("amministrazione", amministrazione, MatchMode.ANYWHERE));
	//	    }
	//	}
	//	if (tutteLeAmministrazioni == false) {
	//	    Integer[] codiciAmministrazioneSistema = configurazioneDAO.getCodiciTutteEStessaAmministrazioniSistema();
	//	    if (null != codiciAmministrazioneSistema) {
	//		det.add(Restrictions.not(Restrictions.in("id.codice", codiciAmministrazioneSistema)));
	//	    }
	//	}
	//	det.addOrder(Order.asc("amministrazione"));
	//	return (List<Amministrazioni>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<String> findToponimiRaggruppati() {

	DetachedCriteria crit = getEmptyCriteriaForClass();
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("prefisso"));
	crit.setProjection(plist);
	crit.addOrder(Order.asc("prefisso"));
	return getHibernateTemplate().findByCriteria(crit);
    }
}
