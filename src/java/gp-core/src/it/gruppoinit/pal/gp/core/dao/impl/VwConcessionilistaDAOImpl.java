package it.gruppoinit.pal.gp.core.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.VwConcessionilistaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.QueryConcessioniListHelper;
import it.gruppoinit.pal.gp.core.dao.helper.TipoQueryHelperEnum;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwConcessionilista;
import it.gruppoinit.pal.gp.core.domain.VwConcessionlistaId;
import it.gruppoinit.pal.gp.core.domain.helper.ConcessioniListHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.VwConcessionilistaService;

@Repository
public class VwConcessionilistaDAOImpl extends BaseDAOImpl<VwConcessionilista, PkId> implements VwConcessionilistaDAO {

    private static final Logger log = LoggerFactory.getLogger(VwConcessionilistaDAOImpl.class);
    private VwConcessionilistaService vwConcessionilistaService;

    @Autowired
    public void setVwConcessionilistaService(VwConcessionilistaService vwConcessionilistaService) {

	this.vwConcessionilistaService = vwConcessionilistaService;
    }

    @Override
    public void delete(VwConcessionilista entity) {

	throw new NotImplementedException();
    }

    @Override
    public Class<VwConcessionilista> getEntityClass() {

	return VwConcessionilista.class;
    }

    @Override
    public void insert(VwConcessionilista entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwConcessionilista entity) {

	throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwConcessionilista> findConcessioniCriteria(VwConcessionilista vwConcessionilista) {

	DetachedCriteria criteria = createCriteria(vwConcessionilista);
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwConcessionilista> findConcessioniCriteria(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults) {

	DetachedCriteria criteria = createCriteria(vwConcessionilista);
	return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    @Override
    public int countByFilter(VwConcessionilista vwConcessionilista) {

	DetachedCriteria criteria = createCriteria(vwConcessionilista);
	criteria.setProjection(Projections.rowCount());
	int ris = ((Integer) getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
	return ris;
    }

    private DetachedCriteria createCriteria(VwConcessionilista vwConcessionilista) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	//////////////////////////////////////////// Filtri per le concessioni ///////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	if (StringUtils.isNotBlank(vwConcessionilista.getConcNumero())) {
	    det.add(Restrictions.eq("concNumero", vwConcessionilista.getConcNumero()));
	}
	if (vwConcessionilista.getConcIdmercato() != 0) {
	    det.add(Restrictions.eq("concIdmercato", vwConcessionilista.getConcIdmercato()));
	}
	if (vwConcessionilista.getConcIdposteggio() != 0) {
	    det.add(Restrictions.eq("concIdposteggio", vwConcessionilista.getConcIdposteggio()));
	}
	if (vwConcessionilista.getConcIdmercatiuso() != 0) {
	    det.add(Restrictions.eq("concIdmercatiuso", vwConcessionilista.getConcIdmercatiuso()));
	}
	if (vwConcessionilista.getIconcCodicecausale() != null && vwConcessionilista.getIconcCodicecausale() != 0) {
	    det.add(Restrictions.eq("iconcCodicecausale", vwConcessionilista.getIconcCodicecausale()));
	}
	if (vwConcessionilista.getDataInizioRilascio() != null) {
	    det.add(Restrictions.ge("concDatarilascio", vwConcessionilista.getDataInizioRilascio()));
	}
	if (vwConcessionilista.getDataFineRilascio() != null) {
	    det.add(Restrictions.le("concDatarilascio", vwConcessionilista.getDataFineRilascio()));
	}
	if (vwConcessionilista.getDataInizioScadenze() != null) {
	    det.add(Restrictions.ge("concDatascadenza", vwConcessionilista.getDataInizioScadenze()));
	}
	if (vwConcessionilista.getDataFineScadenze() != null) {
	    det.add(Restrictions.le("concDatascadenza", vwConcessionilista.getDataFineScadenze()));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getTitolareConcessione(), "id.codice") != null) {
	    det.add(Restrictions.eq("concCodicetitolare", vwConcessionilista.getTitolareConcessione().getId().getCodice()));
	}
	if (!(vwConcessionilista.getIstCodicerichiedente() == null || vwConcessionilista.getIstCodicerichiedente().equals(0))) {
	    det.add(Restrictions.eq("istCodicerichiedente", vwConcessionilista.getIstCodicerichiedente()));
	}
	if (vwConcessionilista.isConcAttiva()) {
	    det.add(Restrictions.eq("concAttiva", Boolean.TRUE));
	}
	///////////////////////////////////////////// Filtri per l'istanza /////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	det.createAlias("istanza", "_istanza");
	if (StringUtils.isNotBlank(vwConcessionilista.getIstanza().getNumeroistanza())) {
	    det.add(Restrictions.eq("_istanza.numeroistanza", vwConcessionilista.getIstanza().getNumeroistanza()));
	}
	if (vwConcessionilista.getIstanzadataDa() != null) {
	    det.add(Restrictions.ge("_istanza.data", vwConcessionilista.getIstanzadataDa()));
	}
	if (vwConcessionilista.getIstanzadataA() != null) {
	    det.add(Restrictions.le("_istanza.data", vwConcessionilista.getIstanzadataA()));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getAlberoproc(), "id.codice") != null) {
	    det.createCriteria("_istanza.alberoproc", "_alberoproc");
	    det.add(Restrictions.eq("_alberoproc.id.codice", vwConcessionilista.getIstanza().getAlberoproc().getId().getCodice()));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getProcedura(), "id.codice") != null) {
	    det.createCriteria("_istanza.procedura", "_procedura");
	    det.add(Restrictions.eq("_procedura.id.codice", vwConcessionilista.getIstanza().getProcedura().getId().getCodice()));
	}
	boolean isIstanzestradarioJoinPresent = false;
	boolean isStradarioJoinPresent = false;
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanzestradario().getStradario(), "id.codice") != null) {
	    det.createCriteria("_istanza.istanzestradarios", "istanzestradario");
	    det.createCriteria("istanzestradario.stradario", "_stradario");
	    det.add(Restrictions.eq("_stradario.id.codice", vwConcessionilista.getIstanzestradario().getStradario().getId().getCodice()));
	    isStradarioJoinPresent = true;
	}
	if (StringUtils.isNotBlank(vwConcessionilista.getIstanzestradario().getCap())) {
	    if (!isStradarioJoinPresent) {
		det.createCriteria("_istanza.istanzestradarios", "istanzestradario");
		isIstanzestradarioJoinPresent = true;
	    }
	    det.add(Restrictions.eq("istanzestradario.cap", vwConcessionilista.getIstanzestradario().getCap()));
	}
	if (vwConcessionilista.getIstanza().getComune() != null
		&& StringUtils.isNotBlank(vwConcessionilista.getIstanza().getComune().getCodicecomune())) {
	    det.createCriteria("_istanza.comune", "_comune");
	    det.add(Restrictions.eq("_comune.codicecomune", vwConcessionilista.getIstanza().getComune().getCodicecomune()));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getRichiedente(), "id.codice") != null) {
	    det.createAlias("_istanza.richiedente", "_richiedenteistanza", Criteria.LEFT_JOIN);
	    det.add(Restrictions.eq("_richiedenteistanza.id.codice", vwConcessionilista.getIstanza().getRichiedente().getId().getCodice()));
	}
	return det;
    }

    @Override
    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults,
	    boolean isSelectGroupByConcId) {

	//
	//	log.debug("findConcessioniListHelper: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	//	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	//	QueryConcessioniListHelper qih = null;
	//	if (isSelectGroupByConcId) {
	//	    qih = new QueryConcessioniListHelper(sessimpl, vwConcessionilista, TipoQueryHelperEnum.SELECT_GROUP_BY_CONC_ID);
	//	} else {
	//	    qih = new QueryConcessioniListHelper(sessimpl, vwConcessionilista, TipoQueryHelperEnum.SELECT);
	//	}
	//	String sql = qih.buildQuery();
	//	SQLQuery q = getSession().createSQLQuery(sql);
	//	if (firstResult != null) {
	//	    q.setFirstResult(firstResult);
	//	}
	//	if (maxResults != null) {
	//	    q.setMaxResults(maxResults);
	//	}
	//	qih.setFilterValues(q);
	//	qih.setScalarProperties(q);
	//	q.setResultTransformer(Transformers.aliasToBean(ConcessioniListHelper.class));
	//	List<ConcessioniListHelper> result = (List<ConcessioniListHelper>) q.list();
	//	return result;
	if (isSelectGroupByConcId) {
	    return this.findConcessioniListHelper(vwConcessionilista, firstResult, maxResults, TipoQueryHelperEnum.SELECT_GROUP_BY_CONC_ID);
	} else {
	    return this.findConcessioniListHelper(vwConcessionilista, firstResult, maxResults, TipoQueryHelperEnum.SELECT);
	}
    }

    @Override
    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults,
	    TipoQueryHelperEnum tipoQueryHelperEnum) {

	log.debug("findConcessioniListHelper: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryConcessioniListHelper qih = new QueryConcessioniListHelper(sessimpl, vwConcessionilista, tipoQueryHelperEnum);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	if (firstResult != null) {
	    q.setFirstResult(firstResult);
	}
	if (maxResults != null) {
	    q.setMaxResults(maxResults);
	}
	qih.setFilterValues(q);
	qih.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(ConcessioniListHelper.class));
	List<ConcessioniListHelper> result = (List<ConcessioniListHelper>) q.list();
	return result;
    }

    @Override
    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults) {

	//
	//	log.debug("findConcessioniListHelper: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	//	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	//	QueryConcessioniListHelper qih = new QueryConcessioniListHelper(sessimpl, vwConcessionilista, TipoQueryHelperEnum.SELECT);
	//	String sql = qih.buildQuery();
	//	SQLQuery q = getSession().createSQLQuery(sql);
	//	if (firstResult != null) {
	//	    q.setFirstResult(firstResult);
	//	}
	//	if (maxResults != null) {
	//	    q.setMaxResults(maxResults);
	//	}
	//	qih.setFilterValues(q);
	//	qih.setScalarProperties(q);
	//	q.setResultTransformer(Transformers.aliasToBean(ConcessioniListHelper.class));
	//	List<ConcessioniListHelper> result = (List<ConcessioniListHelper>) q.list();
	//	return result;
	//	if (BooleanUtils.toBoolean(vwConcessionilista.isConcAttiva()) && vwConcessionilista.getAttiveAllaDataTransient() != null) {
	//	    log.debug("findConcessioniListHelper# Devo processare la lista elimnare i record che hanno lo stesso campo CONC_ID");
	//	    log.debug("findConcessioniListHelper# Dimenzione lista iniziale : {}", result.size());
	//	    List<ConcessioniListHelper> l = processaListConcessioni(result);
	//	    log.debug("findConcessioniListHelper# Dimenzione lista processata : {}", l.size());
	//	    return l;
	//	} else {
	//	    return result;
	//	}
	return this.findConcessioniListHelper(vwConcessionilista, firstResult, maxResults, false);
    }

    /**
     * Elimina i record con lo stesso conc_id
     * 
     * @param l
     * @return
     */
    private List<ConcessioniListHelper> processaListConcessioni(List<ConcessioniListHelper> l) {

	long t1 = System.currentTimeMillis();
	Map<BigInteger, ConcessioniListHelper> m = new HashMap<BigInteger, ConcessioniListHelper>();
	log.debug("processaListConcessioni# Cliclo la lista e popolo la mappa utilizzando come key il valore di Conc_id");
	// Andando ad utilizzare come chiave conc_id, andrò ad eliminare i doppioni in quanto una mappa non può contenere chiavi doppie
	for (ConcessioniListHelper concessioniListHelper : l) {
	    if (!m.containsKey(concessioniListHelper.getConc_id())) {
		m.put(concessioniListHelper.getConc_id(), concessioniListHelper);
	    }
	}
	log.debug("processaListConcessioni# Itero la mappa per popolare nuovamente la lista bonificata");
	List<ConcessioniListHelper> listaProcessata = new ArrayList<ConcessioniListHelper>();
	for (ConcessioniListHelper s : m.values()) {
	    listaProcessata.add(s);
	}
	long t2 = System.currentTimeMillis();
	String time = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(t2 - t1),
		TimeUnit.MILLISECONDS.toSeconds(t2 - t1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(t2 - t1)));
	log.debug("processaListConcessioni# Servito processo in {}", time);
	return listaProcessata;
    }

    @Override
    public int countConcessioniListHelper(VwConcessionilista vwConcessionilista) {

	//	log.debug("countConcessioniListHelper: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	//	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	//	QueryConcessioniListHelper qih = new QueryConcessioniListHelper(sessimpl, vwConcessionilista, TipoQueryHelperEnum.COUNT);
	//	String sql = qih.buildQuery();
	//	SQLQuery q = getSession().createSQLQuery(sql);
	//	qih.setFilterValues(q);
	//	q.addScalar("conteggio_concessioni", Hibernate.BIG_DECIMAL);
	//	List<BigDecimal> rs = q.list();
	//	int ris = 0;
	//	if (!rs.isEmpty()) {
	//	    for (BigDecimal bigDecimal : rs) {
	//		ris += ((BigDecimal) bigDecimal).intValue();
	//	    }
	//	    //ris = ((BigDecimal) rs.get(0)).intValue();
	//	}
	//	return ris;
	return countConcessioniListHelper(vwConcessionilista, false);
    }

    @Override
    public int countConcessioniListHelper(VwConcessionilista vwConcessionilista, boolean isCountDistinctConcId) {

	log.debug("countConcessioniListHelper: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryConcessioniListHelper qih = null;
	if (isCountDistinctConcId) {
	    qih = new QueryConcessioniListHelper(sessimpl, vwConcessionilista, TipoQueryHelperEnum.COUNT_DISTINCT_CONC_ID);
	} else {
	    qih = new QueryConcessioniListHelper(sessimpl, vwConcessionilista, TipoQueryHelperEnum.COUNT);
	}
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	qih.setFilterValues(q);
	q.addScalar("conteggio_concessioni", Hibernate.BIG_DECIMAL);
	List<BigDecimal> rs = q.list();
	int ris = 0;
	if (!rs.isEmpty()) {
	    for (BigDecimal bigDecimal : rs) {
		ris += ((BigDecimal) bigDecimal).intValue();
	    }
	    //ris = ((BigDecimal) rs.get(0)).intValue();
	}
	return ris;
    }

    @Override
    public String exportModalitaPentaho(VwConcessionilista vwConcessionilista, Esportazioni esportazioni, Date data, String email,
	    String contestoExport, boolean isInvioMail) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	log.debug("exportModalitaPentaho: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	if (BooleanUtils.toBoolean(vwConcessionilista.isConcAttiva()) && vwConcessionilista.getAttiveAllaDataTransient() != null) {
	    //	    Session session = this.getSession(false);
	    //	    SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	    List<ConcessioniListHelper> list = new ArrayList<ConcessioniListHelper>();
	    // 1. Recupero tutti i record per i filtri passati collassando i record con select Distinc(CONC_ID,IDCOMUNE) ordinandoli per conc_id e datarilascio
	    // Lista leggera, contiene solo idcomune e conc_id
	    List<ConcessioniListHelper> listTemp = vwConcessionilistaService.findConcessioniListHelper(vwConcessionilista, null, null, true);
	    log.debug("exportModalitaPentaho# Risultato : {}", list.size());
	    // 2. Creo la lista di record da salavare nella tabella temp per l'exèport penthao: Per ogni record filtro per conc_id, idcomune ordinando per conc_id asc 
	    //e data rilascio asc
	    // prendo solo il primo valore e lo metto sulla lista di ritorno
	    VwConcessionilista filterTemp = null;
	    VwConcessionlistaId id = null;
	    // QUALSIASI MODIFICA HAI FILTRI DEVE ESSERE RIPORTATA IN VwConcessionilistaTable.setItems
	    for (ConcessioniListHelper concessioniListHelper : listTemp) {
		filterTemp = new VwConcessionilista();
		id = new VwConcessionlistaId();
		id.setIdcomune(concessioniListHelper.getIdcomune());
		filterTemp.setId(id);
		filterTemp.setConcId(concessioniListHelper.getConc_id().intValue());
		// STEFANO E GIANPAOLO ERRORE EVIDENZIATO DA GALASSI
		filterTemp.setConcAttiva(true);
		filterTemp.setAttiveAllaDataTransient(vwConcessionilista.getAttiveAllaDataTransient());
		//3. Inserisco nella tabella tmp_esportazioni
		List<ConcessioniListHelper> tempList = vwConcessionilistaService.findConcessioniListHelper(filterTemp, null, null,
			TipoQueryHelperEnum.SELECT_WHERE_CONC_ID);
		if (!tempList.isEmpty()) {
		    ConcessioniListHelper concHelperTemp = tempList.get(0);
		    String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
		    String sql = "INSERT INTO " + schema + ".tmp_esportazioni (IDCOMUNE, SESSIONID, CODICE, CODICECOMUNE, DATA) VALUES (?,?,?,?,?)";
		    SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		    sqlQuery = sqlQuery.addScalar("IDCOMUNE", Hibernate.STRING).addScalar("SESSIONID", Hibernate.STRING)
			    .addScalar("CODICE", Hibernate.INTEGER).addScalar("CODICECOMUNE", Hibernate.STRING).addScalar("DATA", Hibernate.DATE);
		    sqlQuery.setString(0, concHelperTemp.getIdcomune());
		    sqlQuery.setString(1, ORMHelper.getToken());
		    sqlQuery.setInteger(2, (concHelperTemp.getOrigine().equalsIgnoreCase("ATTUALE")) ? concHelperTemp.getConc_id().intValue()
			    : concHelperTemp.getProgressivo().intValue());
		    sqlQuery.setString(3, concHelperTemp.getConc_codicecomune());
		    sqlQuery.setDate(4, concHelperTemp.getConc_datavalidita());
		    sqlQuery.executeUpdate();
		}
	    }
	    return "";
	} else {
	    QueryConcessioniListHelper qih = null;
	    String cont = StringUtils.defaultIfEmpty(contestoExport, "CON");
	    // ESISTE UN SOLO CONTESTO
	    //	if (cont.equals("ATS")) {
	    //	    if (data == null) {
	    //		data = new Date();
	    //	    }
	    //	    qih = new QueryIAttivitaHelper(attivitaFilter, sessimpl, alberoprocDAO, statiistanzaDAO, dyn2CampiDAO, false, false, data,
	    //		    TipoQueryIattivitaHelperEnum.PENTAHO_EXP_IN_DATA);
	    //	} 
	    //	else {
	    // TODO modificare per eseguire la query esportazine penthao come in iattivitaDAO (vedi metodo commentato sotto)
	    qih = new QueryConcessioniListHelper(sessimpl, vwConcessionilista, TipoQueryHelperEnum.PENTAHO_EXP);
	    //	    qih = new QueryIAttivitaHelper(attivitaFilter, sessimpl, alberoprocDAO, statiistanzaDAO, dyn2CampiDAO, false, false, null,
	    //		    TipoQueryIattivitaHelperEnum.PENTAHO_EXP);
	    //	}
	    String sql = qih.buildQuery();
	    sql = "insert into tmp_esportazioni(IDCOMUNE, SESSIONID, CODICE, CODICECOMUNE, DATA) ( " + sql + ")";
	    log.debug("exportModalitaPentaho# Query : {}", sql);
	    SQLQuery q = getSession().createSQLQuery(sql);
	    qih.setFilterValues(q);
	    //qih.setScalarProperties(q);
	    q.executeUpdate();
	    return q.getQueryString();
	}
    }
}
