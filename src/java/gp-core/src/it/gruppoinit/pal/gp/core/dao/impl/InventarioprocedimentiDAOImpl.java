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
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
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
	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.add(Restrictions.eq("tipiEndoId", tipiendo.getId().getCodice()));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologia(String textToSearch, Integer codiceFamiglia, Integer codiceTipologia,
	    Boolean escludiDisabilitati) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (codiceTipologia != null) {
	    det.add(Restrictions.eq("tipoendo.id.codice", codiceTipologia));
	}
	if (codiceFamiglia != null) {
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
	if (codicesoftware != null) {
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
    public List<Inventarioprocedimenti> findByTipiendoAndNonAttivatiPerIstanza(Tipiendo tipiendo, Tipifamiglieendo tipifamiglieendo, Istanze istanza,
	    List<String> listCodici, String descrizione, boolean isFiltraTipiEndoNull, Integer maxResult) {

	ChiaveValoreBean<String, Object[]> cvb = getQueryForEndoAttivabili(tipiendo, tipifamiglieendo, istanza, listCodici, descrizione, false,
		isFiltraTipiEndoNull);
	List<Inventarioprocedimenti> result = (List<Inventarioprocedimenti>) getHibernateTemplate().find(cvb.getChiave(), cvb.getValore());
	if (maxResult != null) {
	    int count = result.size();
	    if (count < maxResult) {
		maxResult = count;
	    }
	    return result.subList(0, maxResult);
	}
	return result;
    }

    @Override
    public Integer countByTipiendoAndNonAttivatiPerIstanza(Tipiendo tipiendo, Tipifamiglieendo tipifamiglieendo, Istanze istanza,
	    List<String> listCodici, String descrizione) {

	ChiaveValoreBean<String, Object[]> cvb = getQueryForEndoAttivabili(tipiendo, tipifamiglieendo, istanza, listCodici, descrizione, true, true);
	Long count = ((Long) getHibernateTemplate().iterate(cvb.getChiave(), cvb.getValore()).next());
	return count.intValue();
    }

    /**
     * 
     * @param tipiendo
     *            : filtra per categoria endo (può essere null)
     * @param tipifamiglieendo
     *            filtra per famiglia endo (può essere null)
     * @param istanza
     *            : utilizzato per verificare quelli che giòà sono stati associati all'istanza e li esclude
     * @param listCodici
     *            : lista codici nature per cui filtrare
     * @param descrizione
     *            : campo di testo per la ricerca testuale ajax (può essere null)
     * @param isCount
     *            : se true ritorna il numero dei record se false i record
     * @param isFiltraTipiEndoNull
     *            : se true nel caso di tipo endo null filtra per tipi endo "is null" se false, non applica il filtro su
     *            tipi endo
     * @return
     */
    private ChiaveValoreBean<String, Object[]> getQueryForEndoAttivabili(Tipiendo tipiendo, Tipifamiglieendo tipifamiglieendo, Istanze istanza,
	    List<String> listCodici, String descrizione, boolean isCount, boolean isFiltraTipiEndoNull) {

	String selectField = " this_ ";
	if (isCount) {
	    selectField = " count(*) ";
	}
	String hql = "SELECT " +
		selectField +
		" from Inventarioprocedimenti this_ join this_.amministrazioni a left join this_.tipoendo t  where this_.id.idcomune=? " +
		"and (this_.disabilitato=? or this_.disabilitato is null)";
	Integer codiceTipo = (Integer) EntityUtils.getNestedProperty(tipiendo, "id.codice");
	Integer codiceFamiglia = (Integer) EntityUtils.getNestedProperty(tipifamiglieendo, "id.codice");
	Integer codiceIstanza = (Integer) EntityUtils.getNestedProperty(istanza, "id.codice");
	// Gestione filtro tipo endo
	if (codiceTipo != null) {
	    hql += " and this_.tipiEndoId=? ";
	} else {
	    if (isFiltraTipiEndoNull) {
		hql += " and this_.tipiEndoId is null ";
	    }
	}
	// Gestione filtro famiglia endo
	if (codiceFamiglia != null) {
	    hql += " and t.tipifamiglieendoId=? ";
	}
	if (listCodici != null) {
	    if (!listCodici.isEmpty()) {
		String questionMarks = "";
		for (String codicetipo : listCodici) {
		    questionMarks += "?,";
		}
		if (listCodici != null && listCodici.size() > 0) {
		    questionMarks = questionMarks.substring(0, questionMarks.length() - 1);
		}
		hql += " and this_.naturaendo.id.codice in (" + questionMarks + ")";
	    }
	}
	hql += " and (this_.software.codice = ? or this_.id.codice in (SELECT invsoft_.inventarioprocedimentoId AS y1_ FROM Inventarioprocedimentisoftware " +
		" invsoft_  WHERE invsoft_.id.idcomune=this_.id.idcomune  " +
		" AND invsoft_.inventarioprocedimentoId =this_.id.codice and invsoft_.software.codice=?)) ";
	if (codiceIstanza != null) {
	    hql += " and this_.id.codice not in (SELECT istanz1_.id.codiceinventario AS y0_  FROM Istanzeprocedimenti " +
		    " istanz1_  WHERE istanz1_.id.idcomune=this_.id.idcomune  AND istanz1_.id.codiceistanza=? " +
		    " AND istanz1_.id.codiceinventario =this_.id.codice )";
	}
	// Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	// Query q = s.createQuery(hql);
	int paramPos = 0;
	//q.setString(paramPos, ORMHelper.getIdcomune());
	paramPos++;
	// q.setString(paramPos, false); // disabilitato
	paramPos++;
	if (codiceTipo != null) {
	    //q.setInteger(paramPos, (Integer) EntityUtils.getNestedProperty(tipiendo, "id.codice"));
	    paramPos++;
	}
	// Aumento di uno il valore della dimenzione dell'array da creare per passare i parametri alla query
	if (codiceFamiglia != null) {
	    //q.setInteger(paramPos, (Integer) EntityUtils.getNestedProperty(tipifamiglieendo, "id.codice"));
	    paramPos++;
	}
	if (listCodici != null) {
	    if (!listCodici.isEmpty()) {
		for (String codicetipo : listCodici) {
		    //q.setInteger(paramPos, Integer.valueOf(codicetipo));
		    paramPos++;
		}
	    }
	}
	// Aumento di uno il valore della dimenzione dell'array da creare per passare i parametri alla query per campo stringa procedimento
	if (StringUtils.isNotBlank(descrizione) && !StringUtils.contains(descrizione, "%")) {
	    paramPos++;
	}
	// q.setString(paramPos, ORMHelper.getSoftware());
	paramPos++;
	// q.setString(paramPos, ORMHelper.getSoftware());
	paramPos++;
	if (codiceIstanza != null) {
	    //q.setInteger(paramPos, codiceIstanza);
	    paramPos++;
	}
	// Gestione filtro like sul campo stringa procedimento
	if (StringUtils.isNotBlank(descrizione) && !StringUtils.contains(descrizione, "%")) {
	    hql += " and lower(this_.procedimento) like ?";
	}
	Object[] values = new Object[paramPos];
	paramPos = 0;
	values[paramPos] = ORMHelper.getIdcomune();
	paramPos++;
	values[paramPos] = Boolean.FALSE;
	paramPos++;
	if (codiceTipo != null) {
	    values[paramPos] = codiceTipo;
	    paramPos++;
	}
	// Aggiungo il parametro codice famiglia alla query
	if (codiceFamiglia != null) {
	    values[paramPos] = codiceFamiglia;
	    paramPos++;
	}
	if (listCodici != null) {
	    if (!listCodici.isEmpty()) {
		for (String codicenatura : listCodici) {
		    values[paramPos] = Integer.valueOf(codicenatura);
		    paramPos++;
		}
	    }
	}
	values[paramPos] = ORMHelper.getSoftware();
	paramPos++;
	//values[paramPos] = WebConstants.SOFTWARE_TT;
	values[paramPos] = ORMHelper.getSoftware();
	paramPos++;
	if (codiceIstanza != null) {
	    values[paramPos] = codiceIstanza;
	    paramPos++;
	}
	// Aggiungo il parametro procedimento alla query
	if (StringUtils.isNotBlank(descrizione) && !StringUtils.contains(descrizione, "%")) {
	    descrizione = "%" + descrizione + "%";
	    values[paramPos] = descrizione.toLowerCase();
	    paramPos++;
	}
	// inserisco l'ordinamento
	hql += " ORDER BY this_.ordine asc,lower(this_.procedimento) ASC";
	ChiaveValoreBean<String, Object[]> result = new ChiaveValoreBean<String, Object[]>();
	result.setChiave(hql);
	result.setValore(values);
	return result;
    }

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

    @Override
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologiaGruppiEndo(String textToSearch, Integer codiceFamiglia,
	    Integer codiceTipologia, boolean escludiDisabilitati) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (codiceTipologia != null) {
	    det.add(Restrictions.eq("tipoendo.id.codice", codiceTipologia));
	}
	if (codiceFamiglia != null) {
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
	det.add(Restrictions.isEmpty("gruppiEndoprocedimentiDs"));
	det.addOrder(Order.asc("procedimento"));
	return (List<Inventarioprocedimenti>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Inventarioprocedimenti> findProcedimentiPrincipaliByFilter(String filter) {

	DetachedCriteria crit = this.getIdcomuneCriteria();
	crit.add(Restrictions.in("software.codice", new Object[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }));
	crit.createAlias("alberoprocEndos", "nodo", Criteria.INNER_JOIN);
	crit.add(Restrictions.eq("nodo.flagPrincipale", Boolean.TRUE));
	if (StringUtils.isNotBlank(filter)) {
	    crit.add(Restrictions.ilike("procedimento", filter, MatchMode.ANYWHERE));
	}
	//distinct per eliminare i duplicati che otterrei se uno stesso procedimento fosse associato come principale a più nodi dell'albero
	ProjectionList pl = Projections.projectionList();
	//pl.add(Projections.property("procedimento"), "procedimento").add(Projections.property("id.codice"), "id.codice");
	pl.add(Projections.groupProperty("procedimento")).add(Projections.groupProperty("id.codice"));
	crit.setProjection(pl);
	crit.addOrder(Order.asc("procedimento"));
	crit.setResultTransformer(
		new IgnoreCaseAliasToBeanResultTransformer(Inventarioprocedimenti.class, new String[] { "procedimento", "id.codice" }));
	return (List<Inventarioprocedimenti>) getHibernateTemplate().findByCriteria(crit);
    }
}
