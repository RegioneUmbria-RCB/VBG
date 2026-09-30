package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.GraduatoriedDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.QueryGraduatoriedFilterByCampiSchedaHelper;
import it.gruppoinit.pal.gp.core.domain.Graduatoried;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniDTO;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDTO;
import it.gruppoinit.pal.gp.core.domain.web.GraduatoriedFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.service.CampigraduatoriaService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedService;
import it.gruppoinit.pal.gp.core.service.GraduatorietComService;
import it.gruppoinit.pal.gp.core.service.GraduatorietService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class GraduatoriedDAOImpl extends BaseDAOImpl<Graduatoried, PkId> implements GraduatoriedDAO {

    private static final Logger log = LoggerFactory.getLogger(GraduatoriedDAOImpl.class);
    private Dyn2CampiDAO dyn2CampiDAO;
    private AutorizzazioniService autorizzazioniService;
    private CampigraduatoriaService campigraduatoriaService;
    private GraduatoriedService graduatoriedService;
    private GraduatorietService graduatorietService;
    private GraduatorietComService graduatorietComService;
    private Istanzedyn2datiService istanzedyn2datiService;

    @Autowired
    public void setDyn2CampiDAO(Dyn2CampiDAO dyn2CampiDAO) {

	this.dyn2CampiDAO = dyn2CampiDAO;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setCampigraduatoriaService(CampigraduatoriaService campigraduatoriaService) {

	this.campigraduatoriaService = campigraduatoriaService;
    }

    @Autowired
    public void setGraduatoriedService(GraduatoriedService graduatoriedService) {

	this.graduatoriedService = graduatoriedService;
    }

    @Autowired
    public void setGraduatorietService(GraduatorietService graduatorietService) {

	this.graduatorietService = graduatorietService;
    }

    @Autowired
    public void setGraduatorietComService(GraduatorietComService graduatorietComService) {

	this.graduatorietComService = graduatorietComService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Override
    public Class<Graduatoried> getEntityClass() {

	return Graduatoried.class;
    }

    @SuppressWarnings("unchecked")
    public List<GraduatoriedDTO> findByGraduatoriet(Graduatoriet graduatoriet) {

	DetachedCriteria criteria = getBaseCriteriaFilterByGraduatoriaT(graduatoriet, DAOOrderTypeEnum.ASC);
	List<GraduatoriedDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<GraduatoriedDTO> findByGraduatoriet(Graduatoriet graduatoriet, DAOOrderTypeEnum daoOrderTypeEnum, Integer firstResult,
	    Integer maxResult) {

	DetachedCriteria criteria = getBaseCriteriaFilterByGraduatoriaT(graduatoriet, daoOrderTypeEnum);
	List<GraduatoriedDTO> list = new ArrayList<GraduatoriedDTO>();
	if (null != firstResult && null != maxResult) {
	    list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	} else {
	    list = getHibernateTemplate().findByCriteria(criteria);
	}
	//List<GraduatoriedDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public Set<GraduatoriedDTO> findGraduatoriedPerComunuicazione(Integer codicegraduatoriet, Integer posizioneDa, Integer posizioneA,
	    String destinatari, SchedaDinamicaFilter dinamicaFilter) {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////Gestione filtro Graduatoried.graduatoriat e Graduatoried.posizione  ///////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	Graduatoriet graduatoriet = graduatorietService.findById(new PkId(codicegraduatoriet));
	log.debug("findByGraduatoriet# Recupero GraduatoriedDTO filtrando per graduatoriat e posizione");
	Set<GraduatoriedDTO> graduatoriedDTO2s = new LinkedHashSet<GraduatoriedDTO>();
	log.debug("findGraduatoriedPerComunuicazione# Imposto il criteri base filtrando per graduatoriat {}", graduatoriet.getId().getCodice());
	DetachedCriteria criteria = getBaseCriteriaFilterByGraduatoriaT(graduatoriet, DAOOrderTypeEnum.ASC);
	log.debug("findGraduatoriedPerComunuicazione#Verifico se c'è il filtro posizione graduatoria da impostare ");
	if (null != posizioneDa && null != posizioneA) {
	    log.debug("findGraduatoriedPerComunuicazione#imposto ricerca da posizione {} a posizione {} ", new Object[] { posizioneDa, posizioneA });
	    criteria.add(Restrictions.between("posizione", posizioneDa, posizioneA));
	} else {
	    if (null != posizioneDa && null == posizioneA) {
		log.debug("findGraduatoriedPerComunuicazione#imposto ricerca da posizione maggiore uguale di {}  ", new Object[] { posizioneDa });
		criteria.add(Restrictions.ge("posizione", posizioneDa));
	    }
	    if (null == posizioneDa && null != posizioneA) {
		log.debug("findGraduatoriedPerComunuicazione#imposto ricerca da posizione fino a  {}  ", new Object[] { posizioneA });
		criteria.add(Restrictions.le("posizione", posizioneA));
	    }
	}
	log.debug("findGraduatoriedPerComunuicazione#Inizio query di ricerca.....");
	List<GraduatoriedDTO> graduatoriedDTOs = getHibernateTemplate().findByCriteria(criteria);
	log.debug("findGraduatoriedPerComunuicazione#Fine query di ricerca.....");
	log.debug("findGraduatoriedPerComunuicazione#Verifico se è impostato anche il fitro per scheda dinamicha.....");
	boolean filtroSchedeDinamiche = false;
	GraduatoriedFilter graduatoriedFilter = null;
	if (dinamicaFilter != null && !dinamicaFilter.getRighe().isEmpty()) {
	    graduatoriedFilter = new GraduatoriedFilter();
	    graduatoriedFilter.setSchedaDinamicaFilter(dinamicaFilter);
	    log.debug("findGraduatoriedPerComunuicazione#Filtro per scheda dinamica presente");
	    filtroSchedeDinamiche = true;
	}
	log.debug("findGraduatoriedPerComunuicazione#Popolo per ogni elemento della lista di GraduatoriedDTO il campo concessioni, se presente  ");
	// 
	for (GraduatoriedDTO graduatoried : graduatoriedDTOs) {
	    IstanzeDTO istanza = graduatoried.getIstanza();
	    graduatoried.setBandoOutputList(istanzedyn2datiService.findBandoOutput(graduatoried.getId().getCodice()));
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    /////////////////////Gestione filtro per il campo "Riservato a" GraduatorietCom.destinatari ///////////////////////////////////
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    // Permette di selezionare i record trovati secondo il fitro : istanze senza concessione, istanze con concessione, entrambe
	    List<AutorizzazioniDTO> listConcAndSub = autorizzazioniService.findConcESub(istanza.getId().getCodice(), true);
	    graduatoried.setConcESub(listConcAndSub);
	    // Se vero inserisco sia quelli che hanno concessionni e non e i dati dinamici dell'istanza sono compatibili con 
	    //graduatorietcom.dyn2filtri
	    if (destinatari.equalsIgnoreCase(WebConstants.COMUNICAZIONE_TUTTI_SOGGETTI_GRADUATORIA)) {
		log.debug("findGraduatoriedPerComunuicazione#Riservato a tutti ({}), inserisco nella lista graduatoried con e senza concessioni ",
			destinatari);
		if (filtroSchedeDinamiche) {
		    // Filtro per la scheda e dati dinamici passati
		    graduatoriedFilter.setGraduatoriedDTO(graduatoried);
		    int num_record = this.findExsistGraduatoridFilterByDynDatiIstanza(graduatoriedFilter);
		    if (num_record > 0) {
			graduatoriedDTO2s.add(graduatoried);
		    }
		} else {
		    graduatoriedDTO2s.add(graduatoried);
		}
		// Se vera inserisco solo quelli titolari di concessionei dati dinamici dell'istanza sono compatibili con 
		//graduatorietcom.dyn2filtri
	    } else if (destinatari.equalsIgnoreCase(WebConstants.COMUNICAZIONE_SOLO_TITOLARI_CONCESSIONE)) {
		if (listConcAndSub != null && !listConcAndSub.isEmpty()) {
		    log.debug(
			    "findGraduatoriedPerComunuicazione#Riservato ai titolari di concessione ({}), inserisco nella lista graduatoried con e senza concessioni ",
			    destinatari);
		    if (filtroSchedeDinamiche) {
			// Filtro per la scheda e dati dinamici passati
			graduatoriedFilter.setGraduatoriedDTO(graduatoried);
			int num_record = this.findExsistGraduatoridFilterByDynDatiIstanza(graduatoriedFilter);
			if (num_record > 0) {
			    graduatoriedDTO2s.add(graduatoried);
			}
		    } else {
			graduatoriedDTO2s.add(graduatoried);
		    }
		}
		// Se vera inserisco solo quelli che non sono titolari di concesione e i dati dinamici dell'istanza sono compatibili con 
		//graduatorietcom.dyn2filtri
	    } else if (destinatari.equalsIgnoreCase(WebConstants.COMUNICAZIONE_SOLO_NON_TITOLARI_CONCESSIONI)) {
		if ((listConcAndSub == null || listConcAndSub.isEmpty())) {
		    log.debug(
			    "findGraduatoriedPerComunuicazione#Riservato ai non titolari di concessione ({}), inserisco nella lista graduatoried con e senza concessioni ",
			    destinatari);
		    if (filtroSchedeDinamiche) {
			// Filtro per la scheda e dati dinamici passati
			graduatoriedFilter.setGraduatoriedDTO(graduatoried);
			int num_record = this.findExsistGraduatoridFilterByDynDatiIstanza(graduatoriedFilter);
			if (num_record > 0) {
			    graduatoriedDTO2s.add(graduatoried);
			}
		    } else {
			graduatoriedDTO2s.add(graduatoried);
		    }
		}
	    }
	}
	return graduatoriedDTO2s;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzedyn2dati> findBandoOutput(Graduatoried graduatoried) {

	DetachedCriteria crit = getIdcomuneCriteria();
	DetachedCriteria istanzaCrit = crit.createCriteria("istanza", DetachedCriteria.INNER_JOIN);
	DetachedCriteria gradTCrit = crit.createCriteria("graduatoriet", DetachedCriteria.INNER_JOIN);
	gradTCrit.createCriteria("tipigraduatoriet", DetachedCriteria.INNER_JOIN);
	DetachedCriteria dyn2datiCrit = istanzaCrit.createCriteria("istanzedyn2datis", "i2d", DetachedCriteria.INNER_JOIN);
	DetachedCriteria dyn2campiCrit = dyn2datiCrit.createCriteria("dyn2Campi", DetachedCriteria.INNER_JOIN);
	DetachedCriteria tipiBandoOut = dyn2campiCrit.createCriteria("tipibandooutputsForFkTipibandooutputD2cOut", DetachedCriteria.INNER_JOIN);
	tipiBandoOut.createCriteria("tipigraduatoriet", DetachedCriteria.INNER_JOIN);
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.property("i2d"));
	dyn2datiCrit.setProjection(projectionList);
	crit.add(Restrictions.eq("id.codice", graduatoried.getId().getCodice()));
	List<Istanzedyn2dati> list = (List<Istanzedyn2dati>) getHibernateTemplate().findByCriteria(crit);
	return list;
    }

    /**
     * <pre>
     * Il metodo privato ha il compito di settare il criteri di base di:
     * 		a.SELECT	:
     * 		b.WHERE 	: graduatoriet.id.codice
     * 		c.ORDERBY 	: posizione
     * @param graduatoriet
     * @return criteria :DetachedCriteria
     * </pre>
     */
    private DetachedCriteria getBaseCriteriaFilterByGraduatoriaT(Graduatoriet graduatoriet, DAOOrderTypeEnum daoOrderTypeEnum) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("graduatoriet.id.codice", graduatoriet.getId().getCodice()));
	criteria.createAlias("istanza", "_istanza");
	//criteria.createAlias("_istanza.autorizzazionis", "_aut", DetachedCriteria.LEFT_JOIN);
	//criteria.createAlias("_aut.autorizcomune", "_autorizcomune", DetachedCriteria.LEFT_JOIN);
	//criteria.createAlias("_aut.tipologiaregistro", "_autorizregistro", DetachedCriteria.LEFT_JOIN);
	//criteria.createAlias("_aut.autorizzazioniConcessionisForFkAutconcAutatt", "_conc", DetachedCriteria.LEFT_JOIN);
	//criteria.createAlias("_conc.mercatiD", "_mercatiD", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanza.richiedente", "_richiedente", Criteria.LEFT_JOIN);
	criteria.createAlias("_richiedente.formagiuridica", "_richiedenteFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.titolarelegale", "_titolarelegale", Criteria.LEFT_JOIN);
	criteria.createAlias("_titolarelegale.formagiuridica", "_titolarelegaleFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.tipisoggetto", "_tipisoggetto", Criteria.LEFT_JOIN);
	switch (daoOrderTypeEnum) {
	case ASC:
	    criteria.addOrder(Order.asc("posizione"));
	    break;
	case DESC:
	    criteria.addOrder(Order.desc("posizione"));
	default:
	    criteria.addOrder(Order.asc("posizione"));
	}
	//criteria.addOrder(Order.asc("posizione"));
	ProjectionList plist = Projections.projectionList();
	//GRADUATORIED
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("posizione"), "POSIZIONE");
	//ISTANZA
	plist.add(Projections.property("_istanza.id.codice"), "ISTANZA_ID_CODICE");
	plist.add(Projections.property("_istanza.numeroistanza"), "ISTANZA_NUMEROISTANZA");
	plist.add(Projections.property("_istanza.software.codice"), "ISTANZA_SOFTWARE");
	//CONCESSIONI
	//plist.add(Projections.property("_aut.id.codice"), "CONCESSIONE_ID_CODICE");
	//plist.add(Projections.property("_aut.autoriznumero"), "CONCESSIONE_AUTORIZNUMERO");
	//plist.add(Projections.property("_aut.autorizdata"), "CONCESSIONE_AUTORIZDATA");
	//plist.add(Projections.property("_autorizcomune.comune"), "CONCESSIONE_AUTORIZCOMUNE");
	//plist.add(Projections.property("_autorizregistro.trDescrizione"), "CONCESSIONE_TIPOLOGIAREGISTRO");
	//plist.add(Projections.property("_aut.flagAttiva"), "CONCESSIONE_FLAGATTIVA");
	//plist.add(Projections.property("_aut.dataCessazione"), "CONCESSIONE_DATACESSAZIONE");
	//plist.add(Projections.property("_mercatiD.codiceposteggio"), "CONCESSIONE_CODICEPOSTEGGIO");
	// TIPI SOGGETTO
	plist.add(Projections.property("_tipisoggetto.tiposoggetto"), "ISTANZA_TIPOSOGGETTO");
	//RICHIEDENTE
	plist.add(Projections.property("_richiedente.id.codice"), "ISTANZA_RICHIEDENTE_ID_CODICE");
	plist.add(Projections.property("_richiedente.tipoanagrafe"), "ISTANZA_RICHIEDENTE_TIPOANAGRAFE");
	plist.add(Projections.property("_richiedente.nominativo"), "ISTANZA_RICHIEDENTE_NOMINATIVO");
	plist.add(Projections.property("_richiedente.nome"), "ISTANZA_RICHIEDENTE_NOME");
	plist.add(Projections.property("_richiedente.codicefiscale"), "ISTANZA_RICHIEDENTE_CODICEFISCALE");
	plist.add(Projections.property("_richiedenteFormaGiuridica.formagiuridica"), "ISTANZA_RICHIEDENTE_FORMAGIURIDICA");
	plist.add(Projections.property("_richiedente.partitaiva"), "ISTANZA_RICHIEDENTE_PARTITAIVA");
	plist.add(Projections.property("_richiedente.tipologia"), "ISTANZA_RICHIEDENTE_TIPOLOGIA");
	plist.add(Projections.property("_richiedente.flagDisabilitato"), "ISTANZA_RICHIEDENTE_FLAGDISABILITATO");
	//TITOLARE LEGALE
	plist.add(Projections.property("_titolarelegale.id.codice"), "ISTANZA_TITOLARELEGALE_ID_CODICE");
	plist.add(Projections.property("_titolarelegale.tipoanagrafe"), "ISTANZA_TITOLARELEGALE_TIPOANAGRAFE");
	plist.add(Projections.property("_titolarelegale.nominativo"), "ISTANZA_TITOLARELEGALE_NOMINATIVO");
	plist.add(Projections.property("_titolarelegale.nome"), "ISTANZA_TITOLARELEGALE_NOME");
	plist.add(Projections.property("_titolarelegale.codicefiscale"), "ISTANZA_TITOLARELEGALE_CODICEFISCALE");
	plist.add(Projections.property("_titolarelegaleFormaGiuridica.formagiuridica"), "ISTANZA_TITOLARELEGALE_FORMAGIURIDICA");
	plist.add(Projections.property("_titolarelegale.partitaiva"), "ISTANZA_TITOLARELEGALE_PARTITAIVA");
	plist.add(Projections.property("_titolarelegale.tipologia"), "ISTANZA_TITOLARELEGALE_TIPOLOGIA");
	plist.add(Projections.property("_titolarelegale.flagDisabilitato"), "ISTANZA_TITOLARELEGALE_FLAGDISABILITATO");
	//
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(GraduatoriedDTO.class));
	return criteria;
    }

    @Override
    public int findExsistGraduatoridFilterByDynDatiIstanza(GraduatoriedFilter filter) {

	log.debug("countHelperByFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryGraduatoriedFilterByCampiSchedaHelper qih = new QueryGraduatoriedFilterByCampiSchedaHelper(sessimpl, filter, dyn2CampiDAO);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	qih.setFilterValues(q);
	q.addScalar("cont_graduatoried_compatibili", Hibernate.BIG_DECIMAL);
	List<BigDecimal> rs = q.list();
	int ris = ((BigDecimal) rs.get(0)).intValue();
	return ris;
    }
}
