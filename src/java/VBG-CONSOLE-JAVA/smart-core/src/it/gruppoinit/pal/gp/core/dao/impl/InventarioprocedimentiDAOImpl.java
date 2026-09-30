/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.service.helper.FlagPubblicaEnum;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
@Repository
public class InventarioprocedimentiDAOImpl extends BaseDAOImpl<Inventarioprocedimenti, PkId> implements InventarioprocedimentiDAO {

    @Override
    public Class<Inventarioprocedimenti> getEntityClass() {

	return Inventarioprocedimenti.class;
    }

    @Override
    public List<Inventarioprocedimenti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "procedimento", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Inventarioprocedimenti> findByTipoendo(Tipiendo tipiendo) {

	if (tipiendo == null) {
	    throw new IllegalArgumentException("Il parametro tipiendo non può essere vuoto");
	}
	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria(tipiendo.getId().getIdcomune());
	criteria.add(Restrictions.eq("tipiEndoId", tipiendo.getId().getCodice()));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologia(String textToSearch, Integer codiceFamiglia, String idComuneFamiglia,
	    Integer codiceTipologia, String idComuneTipologia, Boolean escludiDisabilitati) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (codiceTipologia != null) {
	    det = getIdcomuneCriteria(idComuneTipologia);
	    det.add(Restrictions.eq("tipoendo.id.codice", codiceTipologia));
	}
	if (codiceFamiglia != null) {
	    det = getIdcomuneCriteria(idComuneFamiglia);
	    det.createCriteria("tipoendo", "_tipiendo");
	    det.add(Restrictions.eq("_tipiendo.tipifamiglieendo.id.codice", codiceFamiglia));
	}
	if (StringUtils.isNotBlank(textToSearch)) {
	    det.add(Restrictions.ilike("procedimento", textToSearch, MatchMode.ANYWHERE));
	}
	det.add(Restrictions.in("software.codice", new Object[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }));
	if (BooleanUtils.isTrue(escludiDisabilitati)) {
	    det.add(Restrictions.or(Restrictions.eq("disabilitato", false), Restrictions.isNull("disabilitato")));
	}
	det.addOrder(Order.asc("procedimento"));
	return (List<Inventarioprocedimenti>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Inventarioprocedimenti> findAllNonStp() {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.in("software.codice", new Object[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }));
	det.add(Restrictions.eq("disabilitato", false));
	det.add(Restrictions.sizeEq("stpEndoTipo1s", 0));
	det.addOrder(Order.asc("procedimento"));
	return (List<Inventarioprocedimenti>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoAndSoftware(String descrizione, String codicesoftware) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(StringUtils.defaultString(codicesoftware).trim())) {
	    det.add(Restrictions.eq("software.codice", codicesoftware));
	} else {
	    det.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	}
	if (StringUtils.isNotBlank(descrizione)) {
	    det.add(Restrictions.ilike("procedimento", descrizione, MatchMode.ANYWHERE));
	}
	det.addOrder(Order.asc("procedimento"));
	return (List<Inventarioprocedimenti>) getHibernateTemplate().findByCriteria(det);
    }

    //----------------------------------------------------------------------------------------------------------------------------------///
    //------------------------------------------SEZIONE DEDICATA ALLA NUOVA GESTIONE JMESA----------------------------------------------///
    //----------------------------------------------------------------------------------------------------------------------------------///
    @Override
    public int countRecordByFilter(Inventarioprocedimenti inventarioprocedimenti) {

	DetachedCriteria criteria = getCriteria(inventarioprocedimenti);
	criteria.setProjection(Projections.rowCount());
	int ris = ((Integer) getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
	return ris;
    }

    @Override
    public List<Inventarioprocedimenti> findByInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti, Integer startRowPage,
	    Integer endRowPage) {

	DetachedCriteria criteria = getCriteria(inventarioprocedimenti);
	if (null != startRowPage && null != endRowPage) {
	    return (List<Inventarioprocedimenti>) getHibernateTemplate().findByCriteria(criteria, startRowPage.intValue(), endRowPage.intValue());
	} else {
	    return (List<Inventarioprocedimenti>) getHibernateTemplate().findByCriteria(criteria);
	}
    }

    // Criterio di ricerca da applicare ad entrambe le query, conteggio record e ricerca record
    private DetachedCriteria getCriteria(Inventarioprocedimenti inventarioprocedimenti) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.createAlias("tipoendo", "_tipoendo");
	criteria.createAlias("_tipoendo.tipifamiglieendo", "_tipifamiglieendo");
	if (inventarioprocedimenti.getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("id.codice", inventarioprocedimenti.getId().getCodice()));
	}
	if (inventarioprocedimenti.getTipoendo() != null && StringUtils.isNotBlank(inventarioprocedimenti.getTipoendo().getTipo())) {
	    criteria.add(Restrictions.ilike("_tipoendo.tipo", inventarioprocedimenti.getTipoendo().getTipo(), MatchMode.ANYWHERE));
	}
	if (inventarioprocedimenti.getTipoendo() != null && inventarioprocedimenti.getTipoendo().getTipifamiglieendo() != null
		&& StringUtils.isNotBlank(inventarioprocedimenti.getTipoendo().getTipifamiglieendo().getTipo())) {
	    criteria.add(Restrictions.ilike("_tipifamiglieendo.tipo", inventarioprocedimenti.getTipoendo().getTipifamiglieendo().getTipo(),
		    MatchMode.ANYWHERE));
	}
	return criteria;
    }

    //----------------------------------------------------------------------------------------------------------------------------------///
    //---------------------------------------------------------------END----------------------------------------------------------------///
    //----------------------------------------------------------------------------------------------------------------------------------///
    @Override
    public List<Integer> findCodiciEndoPerSoftware(String software) {

	String hql = "Select i.id.codice from Inventarioprocedimenti i where i.id.idcomune=? and i.software.codice=? ";
	Object[] values = null;
	values = new Object[] { ORMHelper.getIdcomune(), software };
	return getHibernateTemplate().find(hql, values);
    }

    @Override
    public List<Tipifamiglieendo> findTutteFamiglieEndoByCurrSoftwareAndTT(FlagPubblicaEnum flagPubblicaEnum) {

	Boolean pubblica = null;
	switch (flagPubblicaEnum) {
	case DA_PUBBLICARE:
	    pubblica = Boolean.TRUE;
	    break;
	case NON_PUBBLICARE:
	    pubblica = Boolean.FALSE;
	    break;
	default:
	    break;
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.in("software.codice", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }));
	Criterion disabilitatoNull = Restrictions.isNull("disabilitato");
	Criterion disabilitato0 = Restrictions.eq("disabilitato", Boolean.FALSE);
	criteria.add(Restrictions.or(disabilitatoNull, disabilitato0));
	// Setto condizioni di join
	criteria.createAlias("tipoendo", "_tipoendo", Criteria.LEFT_JOIN);
	criteria.createAlias("_tipoendo.tipifamiglieendo", "_tipifamiglieendo", Criteria.LEFT_JOIN);
	// verifica il flag pubblica su tipofamiglia endo
	if (pubblica != null) {
	    criteria.add(Restrictions.eq("_tipifamiglieendo.flagPubblica", pubblica));
	}
	//Setto i campi per cui vogliamo fare la projection
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("_tipifamiglieendo.id.codice"), "ID_CODICE");
	plist.add(Projections.property("_tipifamiglieendo.tipo"), "TIPO");
	plist.add(Projections.groupProperty("_tipifamiglieendo.id.codice"));
	plist.add(Projections.groupProperty("_tipifamiglieendo.tipo"));
	plist.add(Projections.groupProperty("_tipifamiglieendo.ordine"));
	// Setto condizioni di ordinamento	
	criteria.addOrder(Order.asc("_tipifamiglieendo.ordine"));
	criteria.addOrder(Order.asc("_tipifamiglieendo.tipo"));
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(Tipifamiglieendo.class));
	List<Tipifamiglieendo> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<Tipiendo> findTutteTipologieEndoByCurrSoftwareAndTT(Integer codiceFamiglia, FlagPubblicaEnum pubblicaEnum) {

	Boolean pubblica = null;
	switch (pubblicaEnum) {
	case DA_PUBBLICARE:
	    pubblica = new Boolean(true);
	    break;
	case NON_PUBBLICARE:
	    pubblica = new Boolean(false);
	    break;
	default:
	    break;
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.in("software.codice", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }));
	Criterion disabilitatoNull = Restrictions.isNull("disabilitato");
	Criterion disabilitato0 = Restrictions.eq("disabilitato", Boolean.FALSE);
	criteria.createAlias("tipoendo", "_tipoendo", Criteria.LEFT_JOIN);
	criteria.add(Restrictions.or(disabilitatoNull, disabilitato0));
	if (codiceFamiglia == null || codiceFamiglia.intValue() == -1) {
	    criteria.add(Restrictions.isNull("_tipoendo.tipifamiglieendoId"));
	} else {
	    criteria.add(Restrictions.eq("_tipoendo.tipifamiglieendoId", codiceFamiglia));
	}
	// verifica il flag pubblica su tipofamiglia endo
	if (pubblica != null) {
	    criteria.add(Restrictions.eq("_tipoendo.flagPubblica", pubblica));
	}
	// Setto condizioni di join
	//Setto i campi per cui vogliamo fare la projection
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("_tipoendo.id.codice"), "ID_CODICE");
	plist.add(Projections.property("_tipoendo.tipo"), "TIPO");
	plist.add(Projections.groupProperty("_tipoendo.id.codice"));
	plist.add(Projections.groupProperty("_tipoendo.tipo"));
	plist.add(Projections.groupProperty("_tipoendo.ordine"));
	// Setto condizioni di ordinamento	
	criteria.addOrder(Order.asc("_tipoendo.ordine"));
	criteria.addOrder(Order.asc("_tipoendo.tipo"));
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(Tipiendo.class));
	List<Tipiendo> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }
}
