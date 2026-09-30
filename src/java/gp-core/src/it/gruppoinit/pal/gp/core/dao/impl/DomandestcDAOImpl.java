package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.DomandestcDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.QueryDomandeStcScadenzarioDTOHelper;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.helper.DomandeSTCScadenzarioDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DomandestcHelper;
import it.gruppoinit.pal.gp.core.domain.web.DomandeStcFilter;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class DomandestcDAOImpl extends BaseDAOImpl<Domandestc, PkId> implements DomandestcDAO {

    private static final Logger log = LoggerFactory.getLogger(DomandestcDAOImpl.class);
    private ComuniassociatiService comuniassociatiService;
    private ResponsabilisoftwareService responsabilisoftwareService;
    private ResponsabilicomuniService responsabilicomuniService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setResponsabilisoftwareService(ResponsabilisoftwareService responsabilisoftwareService) {

	this.responsabilisoftwareService = responsabilisoftwareService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setResponsabilicomuniService(ResponsabilicomuniService responsabilicomuniService) {

	this.responsabilicomuniService = responsabilicomuniService;
    }

    @Override
    public Class<Domandestc> getEntityClass() {

	return Domandestc.class;
    }

    @Override
    public List<Domandestc> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }

    @Override
    public List<DomandestcHelper> countDomandestc(String codicesoftware, String stato) {

	// Filtro per codice comune, se è un installazione multi comune, l'operatore deve vedere solo le scadenze per i comuni 
	// per cui è abilitato.
	if (log.isDebugEnabled()) {
	    log.debug("countDomandestc# Controllo se si tratta di un installazione con idcomune {} è multi comune", ORMHelper.getIdcomune());
	}
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	if (StringUtils.isNotBlank(schemaName)) {
	    schemaName = schemaName + ".";
	}
	String sql = "SELECT count(*) AS numero, d.idcomune as idcomune,d.codicesoftware as codicesoftware, d.id_nodo as idnodo, "
		+ " d.id_entemitt as identemittente, d.id_sportellomitt as idsportellomittente FROM " + schemaName + "domandestc d ";
	// Se è presente lo stato o è multicomune allora devo fare la join 
	// per tra domandestc e istanze 
	if (StringUtils.isNotBlank(stato) || isComuniAssociati) {
	    sql += " inner join " + schemaName + "istanze i on ";
	    sql += " i.idcomune=d.idcomune and i.codiceistanza=d.codiceistanza ";
	}
	// Se lo stato è passato metto la condizione
	if (StringUtils.isNotBlank(stato)) {
	    sql += " and i.chiusura=? ";
	}
	// se è comuni associati faccio la join tra istanze e la tabella comuni
	if (isComuniAssociati) {
	    sql += " inner join " + schemaName + "comuni c on ";
	    sql += " c.codicecomune=i.codicecomune ";
	}
	sql += " WHERE d.idcomune =? AND d.codicesoftware=? AND d.flag_import=? ";
	String[] codicicomune = null;
	if (isComuniAssociati) {
	    if (log.isDebugEnabled()) {
		log.debug("countDomandestc# E' un installazione multicomune, recupero i comuni configurati per l'opertaore)");
	    }
	    List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile(false);
	    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
		codicicomune = new String[responsabilicomunis.size()];
		sql += " and (c.codicecomune in  (";
		String comuniattiviQm = "";
		for (int i = 0; i < responsabilicomunis.size(); i++) {
		    Responsabilicomuni responsabilicomunimuni = (Responsabilicomuni) responsabilicomunis.get(i);
		    codicicomune[i] = responsabilicomunimuni.getId().getCodicecomune();
		    comuniattiviQm += ",?";
		}
		comuniattiviQm = comuniattiviQm.replaceFirst(",", "");
		sql += comuniattiviQm + "))";
	    }
	}
	sql += " GROUP BY d.idcomune, d.codicesoftware, d.id_nodo,  d.id_entemitt,  d.id_sportellomitt";
	SQLQuery q = getSession().createSQLQuery(sql);
	int position = 0;
	if (StringUtils.isNotBlank(stato)) {
	    q.setString(position, stato);
	    position++;
	}
	q.setString(position, ORMHelper.getIdcomune());
	position++;
	q.setString(position, codicesoftware);
	position++;
	q.setInteger(position, 1);
	position++;
	// Se è installazione multi comune  aggiungo il parametro che contiete tutti i codici dei
	// comuni attivi per l'operatore
	if (isComuniAssociati) {
	    for (int i = 0; i < codicicomune.length; i++) {
		q.setString(position, codicicomune[i]);
		position++;
	    }
	}
	q.addScalar("numero", Hibernate.INTEGER);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("codicesoftware", Hibernate.STRING);
	q.addScalar("idnodo", Hibernate.STRING);
	q.addScalar("identemittente", Hibernate.STRING);
	q.addScalar("idsportellomittente", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(DomandestcHelper.class));
	List<DomandestcHelper> result = q.list();
	return result;
    }

    @Override
    public List<DomandeSTCScadenzarioDTO> findDomandePervenuteSTC(DomandeStcFilter domandeStcFilter, boolean isImportate, String codiceSoftwareScad,
	    Integer firstResult, Integer maxResult) {

	log.debug("findDomandePervenuteSTC: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	// Nel caso stiamo cercando le pratiche importate corretamente utilizzo una query sql
	if (isImportate) {
	    SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	    QueryDomandeStcScadenzarioDTOHelper qih = new QueryDomandeStcScadenzarioDTOHelper(sessimpl, userSecurityService,
		    responsabilisoftwareService, comuniassociatiService, responsabilicomuniService, false, domandeStcFilter, isImportate,
		    codiceSoftwareScad);
	    String sql = qih.buildQuery();
	    SQLQuery q = getSession().createSQLQuery(sql);
	    if (firstResult != null) {
		q.setFirstResult(firstResult);
	    }
	    if (maxResult != null) {
		q.setMaxResults(maxResult);
	    }
	    qih.setFilterValues(q);
	    qih.setScalarProperties(q);
	    q.setResultTransformer(Transformers.aliasToBean(DomandeSTCScadenzarioDTO.class));
	    List<DomandeSTCScadenzarioDTO> result = (List<DomandeSTCScadenzarioDTO>) q.list();
	    return result;
	} else {// Nel caso di domande non importate la query va fatta direttamente solo sulla tabella DOMANDESTC uso i criteri
	    DetachedCriteria criteria = getIdcomuneCriteria();
	    ProjectionList plist = Projections.projectionList();
	    //	    /////////////////// Definisco condizioni di JOIN  //////////////////////////////////////////////////////
	    criteria.createAlias("software", "_software");
	    //	    criteria.createAlias("istanza", "_istanza");
	    //criteria.createAlias("_istanza.istruttore", "_istruttore");
	    ////////////////////////////////////Condizioni di SELECT///////////////////////////////////////////////////
	    //
	    plist.add(Projections.property("numeroistanza"), "NUMEROISTANZA");
	    plist.add(Projections.property("richiedente"), "NOMINATIVO");
	    plist.add(Projections.property("idDomandamitt"), "MITTENTEDOMANDA");
	    plist.add(Projections.property("idEntemitt"), "ENTEMITTENTE");
	    plist.add(Projections.property("ultimoerrore"), "ULTIMOERRORE");
	    plist.add(Projections.property("idSportellomitt"), "SPORTELLODOMANDA");
	    plist.add(Projections.property("_software.codice"), "CODSOFTWARE");
	    plist.add(Projections.property("_software.descrizione"), "DESCRIZIONESOFTWARE");
	    // plist.add(Projections.property("_istruttore.responsabile"), "ISTRUTTORE");
	    //plist.add(Projections.property("_comune.comune"), "COMUNE");
	    //	}
	    plist.add(Projections.property("idNodo"), "IDNODO");
	    criteria.setProjection(plist);
	    //	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	    //	//////////////////////////////////// Condizioni di WHERE///////////////////////////////////////////////////
	    //	//gestione del filtro software, nel caso sia TT devono essere impostati come filtri tutti i software 
	    //	// attivi per l'operatore loggato, altrimenti solo quello corrente
	    Responsabili responsabili = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    if (ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
		List<String> codicisoftware = new ArrayList<String>();
		List<Responsabilisoftware> responsabilisoftwares = responsabilisoftwareService.findByResponsabile(responsabili);
		for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
		    codicisoftware.add(responsabilisoftware.getSoftware().getCodice());
		}
		criteria.add(Restrictions.in("_software.codice", codicisoftware));
	    } else {
		criteria.add(Restrictions.eq("_software.codice", ORMHelper.getSoftware()));
	    }
	    criteria.add(Restrictions.eq("flagImport", new Boolean(isImportate)));
	    //	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	    //	//////////////////////////////////// Condizioni di ORDINAMENTO///////////////////////////////////////////////////
	    criteria.addOrder(Order.asc("_software.descrizione"));
	    // Effettuo la conversazione nel DTO creato
	    criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(DomandeSTCScadenzarioDTO.class));
	    if (null != firstResult && null != maxResult) {
		List<DomandeSTCScadenzarioDTO> list = (List<DomandeSTCScadenzarioDTO>) getHibernateTemplate().findByCriteria(criteria,
			firstResult.intValue(), maxResult.intValue());
		return list;
	    } else {
		List<DomandeSTCScadenzarioDTO> list = (List<DomandeSTCScadenzarioDTO>) getHibernateTemplate().findByCriteria(criteria);
		return list;
	    }
	}
    }

    @Override
    public int countScadenzarioDomandePervenuteSTC(boolean isImportate, String codiceSoftwareScad,DomandeStcFilter domandeStcFilter) {

	int ris = 0;
	// Nel caso stiamo cercando le pratiche importate corretamente utilizzo una query sql
	if (isImportate) {
	    log.debug("findDomandePervenuteSTC: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	    SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	    QueryDomandeStcScadenzarioDTOHelper qih = new QueryDomandeStcScadenzarioDTOHelper(sessimpl, userSecurityService,
		    responsabilisoftwareService, comuniassociatiService, responsabilicomuniService, true, domandeStcFilter, isImportate,
		    codiceSoftwareScad);
	    String sql = qih.buildQuery();
	    SQLQuery q = getSession().createSQLQuery(sql);
	    qih.setFilterValues(q);
	    q.addScalar("conteggio_domandestc", Hibernate.BIG_DECIMAL);
	    List<BigDecimal> rs = q.list();
	    ris = ((BigDecimal) rs.get(0)).intValue();
	} else {// Nel caso di domande non importate la query va fatta direttamente solo sulla tabella DOMANDESTC uso i criteri
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("flagImport", isImportate, Boolean.class));
	    //	
	    Responsabili responsabili = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    if (ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
		List<String> codicisoftware = new ArrayList<String>();
		List<Responsabilisoftware> responsabilisoftwares = responsabilisoftwareService.findByResponsabile(responsabili);
		for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
		    codicisoftware.add(responsabilisoftware.getSoftware().getCodice());
		}
		fr.addFilterField(FilterUtils.in("codice", codicisoftware.toArray(), "software", String.class));
	    } else {
		fr.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	    }
	    // Devo controllare anche che non stiamo controllando le istanze non importate in quanto queste non hanno 
	    // un codice comune. Il codice comune è legato all'istanza vera e propria inserita e in questo caso 
	    // non ne esiste una.
	    ft.addRestriction(fr);
	    ris = this.countRecord(ft);
	}
	return ris;
    }
}
