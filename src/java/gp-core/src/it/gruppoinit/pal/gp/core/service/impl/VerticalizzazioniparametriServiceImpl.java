package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniparametriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.SoftwareattiviId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniparametriComparator;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniparametriHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;

/**
 * 
 * @author gianpaolot
 */
@Service
public class VerticalizzazioniparametriServiceImpl extends BaseServiceImpl<Verticalizzazioniparametri, PkId>
	implements VerticalizzazioniparametriService {

    private static Logger log = LoggerFactory.getLogger(VerticalizzazioniparametriServiceImpl.class);
    private ComuniService comuniService;
    private ComuniassociatiService comuniassociatiService;
    private VerticalizzazioniparametriDAO verticalizzazioniparametriDAO;
    private VerticalizzazioniService verticalizzazioniService;
    private SoftwareattiviService softwareattiviService;
    private SoftwareService softwareService;
    private ResponsabiliService responsabiliService;
    private UserSecurityService userSecurityService;
    private AmministrazioniService amministrazioniService;
    private Map<String, String> nodiSTC;

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setSoftwareattiviService(SoftwareattiviService softwareattiviService) {

	this.softwareattiviService = softwareattiviService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setVerticalizzazioniparametriDAO(VerticalizzazioniparametriDAO verticalizzazioniparametriDAO) {

	this.verticalizzazioniparametriDAO = verticalizzazioniparametriDAO;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Override
    protected Class<Verticalizzazioniparametri> getEntityClass() {

	return Verticalizzazioniparametri.class;
    }

    @Override
    public List<Verticalizzazioniparametri> findAll(Integer firstResult, Integer maxResult) {

	return verticalizzazioniparametriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Verticalizzazioniparametri entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && insertAllowed(entity)) {
	    verticalizzazioniparametriDAO.insert(entity);
	}
    }

    @Override
    public Verticalizzazioniparametri findById(PkId id) {

	return verticalizzazioniparametriDAO.findById(id);
    }

    @Override
    public void update(Verticalizzazioniparametri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    verticalizzazioniparametriDAO.update(entity);
	}
    }

    private void dataIntegration(Verticalizzazioniparametri entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Entity non può essere nulla");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Verticalizzazioniparametri entity) {

	Comuni c = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(c);
	Software s = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(s);
    }

    @Override
    public void delete(Verticalizzazioniparametri entity) {

	if (isDeleteAllowed(entity)) {
	    verticalizzazioniparametriDAO.delete(entity);
	}
    }

    @Override
    public List<Verticalizzazioniparametri> findByModuloAndIdcomuneAndSoftware(String modulo, String idcomune, String software) {

	return verticalizzazioniparametriDAO.findByModuloAndIdcomuneAndSoftware(modulo, idcomune, software);
    }

    @Override
    public List<VerticalizzazioniparametriHelper> findVerticalizzazioniparametriHelperAndCheckConfigurabilePerOperatore(String modulo,
	    String software, String codiceComune) {

	List<VerticalizzazioniparametriHelper> listParametri = new ArrayList<VerticalizzazioniparametriHelper>();
	List<Softwareattivi> softwareattiviList = new ArrayList<Softwareattivi>();
	if (software.equals(WebConstants.SOFTWARE_TT)) {
	    softwareattiviList = softwareattiviService.findAll(null, null);
	} else {
	    SoftwareattiviId id = new SoftwareattiviId(software);
	    Softwareattivi softwareattivi = softwareattiviService.findById(id);
	    softwareattiviList.add(softwareattivi);
	}
	// per ogni software attivo, per il comune trovo la lista dei parametri della verticalizzazione
	for (Softwareattivi softwareattivi : softwareattiviList) {
	    List<Verticalizzazioniparametri> listTemp = this.findByModuloAndIdcomuneAndSoftwareAndComune(modulo, codiceComune,
		    softwareattivi.getId().getFkSoftware());
	    //FIXME perdo ordinamento
	    List<Verticalizzazioniparametri> list = setFlagSoftwarePerAbilitatotransiet(listTemp);
	    Collections.sort(list, new VerticalizzazioniparametriComparator());
	    VerticalizzazioniparametriHelper verticalizzazioniparametriHelper = null;
	    if (!list.isEmpty()) {
		verticalizzazioniparametriHelper = new VerticalizzazioniparametriHelper();
		Software softwareObj = softwareService.findById(softwareattivi.getId().getFkSoftware());
		verticalizzazioniparametriHelper.setSoftware(softwareObj);
		verticalizzazioniparametriHelper.setVerticalizzazioniparametris(list);
	    }
	    if (verticalizzazioniparametriHelper != null)
		listParametri.add(verticalizzazioniparametriHelper);
	}
	return listParametri;
    }

    @Override
    public List<VerticalizzazioniparametriHelper> findVerticalizzazioniparametriHelperAndCheckConfigurabilePerOperatoreAndComune(String modulo,
	    String comune, Set<Responsabilisoftware> responsabilisoftwares) {

	List<VerticalizzazioniparametriHelper> listParametri = new ArrayList<VerticalizzazioniparametriHelper>();
	List<Softwareattivi> softwareattiviList = softwareattiviService.findAll(null, null);
	// per ogni software attivo, per il comune trovo la lista dei parametri della verticalizzazione
	for (Softwareattivi softwareattivi : softwareattiviList) {
	    List<Verticalizzazioniparametri> listTemp = this.findByModuloAndIdcomuneAndSoftwareAndComune(modulo, comune,
		    softwareattivi.getSoftware().getCodice());
	    //FIXME perdo ordinamento
	    List<Verticalizzazioniparametri> list = setFlagSoftwarePerAbilitatotransiet(listTemp);
	    VerticalizzazioniparametriHelper verticalizzazioniparametriHelper = null;
	    if (!list.isEmpty()) {
		verticalizzazioniparametriHelper = new VerticalizzazioniparametriHelper();
		verticalizzazioniparametriHelper.setSoftware(softwareattivi.getSoftware());
		verticalizzazioniparametriHelper.setVerticalizzazioniparametris(list);
	    }
	    if (verticalizzazioniparametriHelper != null)
		listParametri.add(verticalizzazioniparametriHelper);
	}
	return listParametri;
    }

    @Override
    public List<Verticalizzazioniparametri> findByModuloAndIdcomuneAndSoftwareAndComune(String modulo, String comune, String software) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("software.codice", new Object[] { software }, String.class));
	fr.addFilterField(FilterUtils.equals("verticalizzazioniparametribase.id.modulo", modulo, String.class));
	if (StringUtils.isNotBlank(comune)) {
	    fr.addFilterField(FilterUtils.equals("codicecomune", comune, "comune", String.class));
	} else {
	    fr.addFilterField(FilterUtils.isNull("comune"));
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("moduloopzionale", "software"));
	ft.addOrder(FilterUtils.orderDesc("ordine", "software"));
	List<Verticalizzazioniparametri> list = verticalizzazioniparametriDAO.findByFilterTable(ft);
	return list;
    }

    /**
     * Il metodo setta ad ogni elemento della lista di Verticalizzazioneparametri il campo transiet
     * "flagSoftwarePerAbilitatotransiet" secondo la logica: true: se il software collegato al recordo è configurato per
     * l'operatore loggato , altrimenti false
     * 
     * @param list
     * @return
     */
    private List<Verticalizzazioniparametri> setFlagSoftwarePerAbilitatotransiet(List<Verticalizzazioniparametri> list) {

	// Recupero il Responsabile loggato
	LoggedUser userDetail = (LoggedUser) userSecurityService.getCurrentlyAuthenticatedUser();
	Responsabili responsabile = responsabiliService.findById(new PkId(userDetail.getCodiceResponsabile()));
	Set<Responsabilisoftware> softwaresAbilitati = responsabile.getSoftwareAbilitati();
	List<Verticalizzazioniparametri> risultato = new ArrayList<Verticalizzazioniparametri>();
	// controllo per ogni verticalizzazione se il software per cui è configurata è ablitato all'utente loggato
	int i = 0;
	for (Verticalizzazioniparametri verticalizzazioniparametri : list) {
	    for (Responsabilisoftware responsabilisoftware : softwaresAbilitati) {
		if (verticalizzazioniparametri.getSoftware().getCodice().equals(responsabilisoftware.getSoftware().getCodice())) {
		    verticalizzazioniparametri.setFlagSoftwarePerAbilitatotransiet(true);
		    risultato.add(i, verticalizzazioniparametri);
		    break;
		}
	    }
	    // non è stato trovato il sotware abilitato per l'utente allora lo setto a false
	    if (verticalizzazioniparametri.getFlagSoftwarePerAbilitatotransiet() == null) {
		verticalizzazioniparametri.setFlagSoftwarePerAbilitatotransiet(false);
		risultato.add(i, verticalizzazioniparametri);
	    }
	    i++;
	}
	return risultato;
    }

    @Override
    public String findNomeNodoModuloSTC(String value) {

	if (log.isDebugEnabled()) {
	    log.debug("findNomeNodoModuloSTC#Recupero il nome del parametro associato al valore passato");
	}
	String idnodo = "";
	// Controllo che la mappa non sia null, nel caso la creao
	if (nodiSTC == null) {
	    nodiSTC = new HashMap<String, String>();
	}
	// Genero la chiave per recuperare il valore
	String key = getNodoKey(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(), value);
	if (log.isDebugEnabled()) {
	    log.debug("findNomeNodoModuloSTC#Cerco nome parametro nella mappa per la chiave: {}", key);
	}
	// Controllo se esiste il valore per la chiave trovata.
	if (nodiSTC.get(key) == null) {
	    if (log.isDebugEnabled()) {
		log.debug("findNomeNodoModuloSTC#Valore non trovato nella mappa, lo cerco sul DB");
	    }
	    // Non ho trovato nessun valore lo cerco sul BD, prima per il software corrente e poi per software TT
	    List<Verticalizzazioniparametri> verticalizzazioniparametri = this.findParametriConfiguratiByModulo(WebConstants.VERTICALIZZAZIONE_STC);
	    String chiave = "";
	    // Controllo se esiste questo parametro
	    if (verticalizzazioniparametri != null) {
		for (Verticalizzazioniparametri vp : verticalizzazioniparametri) {
		    if (vp.getVerticalizzazioniparametribase().getId().getParametro().startsWith("NLA_IDNODO")) {
			if (StringUtils.defaultString(vp.getValore()).equalsIgnoreCase(value)) {
			    chiave = vp.getVerticalizzazioniparametribase().getId().getParametro();
			    break;
			}
		    }
		}
		nodiSTC.put(key, chiave);
		idnodo = chiave;
		//		}
	    } else {
		// Non ho trovato nessun valore, ritorno servizio come stringa vuota
		if (log.isDebugEnabled()) {
		    log.debug("findNomeNodoModuloSTC#Parametro non trovato per la verticalizzazione {} e  valore {}",
			    new Object[] { WebConstants.VERTICALIZZAZIONE_STC, value });
		}
		idnodo = "";
	    }
	} else {// Ho trovato il valore all'interno della mappa per il software corrente 
		// recupero il valore e lo setto alla variabile idnodo
	    if (log.isDebugEnabled()) {
		log.debug("findNomeNodoModuloSTC#Valore trovato nella mappa");
	    }
	    idnodo = nodiSTC.get(key);
	}
	return idnodo;
    }

    @Override
    public String decodeNomeNodoModuloSTC(String idNodo, String idEnte, String idSportello) {

	if (log.isDebugEnabled()) {
	    log.debug("decodeNomeNodoModuloSTC#Inizio decodifica nome servizio");
	}
	// Recupero il nome del nodo apartire dal codice (ES 1200-->NLA_IDNODO_AREARISERVATA)
	String nomeNodo = this.findNomeNodoModuloSTC(idNodo);
	// Decodifico il nome del servizio a partire da nome del nodo  (Es. NLA_IDNODO_AREARISERVATA-->AREARISERVATA)
	String nomeServizio = "";
	if (StringUtils.isNotBlank(nomeNodo)) {
	    // Controllo se il parametro contiene la stringa NLA_IDNODO
	    if (StringUtils.contains(nomeNodo, "NLA_IDNODO")) {
		nomeServizio = StringUtils.remove(nomeNodo, "NLA_IDNODO");
		if (StringUtils.isBlank(nomeServizio)) {
		    // nomeServizio = "BACKOFFICE";
		    if (log.isDebugEnabled()) {
			log.debug("decodeNomeNodoModuloSTC#Srvizio decodificato : BACKOFFICE");
		    }
		    // Recupero il modulo software da cui è stata inviata la pratica
		    String codiceSoftware = idSportello;
		    if (idSportello.indexOf("#") >= 0) { //  ES CO#PEC
			codiceSoftware = idSportello.substring(0, idSportello.indexOf("#"));
		    }
		    Software software = softwareService.findById(codiceSoftware);
		    if (software != null && StringUtils.isNotBlank(software.getCodice())) {
			nomeServizio = software.getDescrizione().toUpperCase();
		    } else {
			log.info("decodeNomeNodoModuloSTC#Id mittente non passato o non riconosciuto");
			nomeServizio = "NON_DEFINITO";
		    }
		} else {
		    nomeServizio = StringUtils.substring(nomeServizio, 1);
		}
	    } else {
		log.info("decodeNomeNodoModuloSTC#Nome nodo non riconosciuto");
		nomeServizio = "NON_DEFINITO";
	    }
	} else {
	    log.info("decodeNomeNodoModuloSTC#Codice nodo non passato");
	    nomeServizio = "NON_DEFINITO";
	}
	if (nomeServizio == "NON_DEFINITO") {
	    // Ricavo l'amministrazione
	    Amministrazioni amministrazione = amministrazioniService.findAmministrazioneSTC(idNodo, idEnte, idSportello, null);
	    if (amministrazione != null && EntityUtils.getNestedProperty(amministrazione, "id.codice") != null) {
		nomeServizio = amministrazione.getAmministrazione().toUpperCase();
	    } else {
		log.info("decodeNomeNodoModuloSTC#Amministrazione non trovata");
		nomeServizio = idNodo + "_" + idEnte + "_" + idSportello;
	    }
	}
	return nomeServizio;
    }

    public Verticalizzazioniparametri findVerticalizzazioniparametriByModuloAndValue(String modulo, String value) {

	Verticalizzazioniparametri verticalizzazioniparametri = null;
	List<Verticalizzazioniparametri> listParametri = new ArrayList<Verticalizzazioniparametri>();
	FilterTable ft = null;
	if (log.isDebugEnabled()) {
	    log.debug("findVerticalizzazioniparametriByModuloAndValue#Controllo se verticalizzazione {} è attiva", modulo);
	}
	if (verticalizzazioniService.isAttiva(modulo)) {
	    if (log.isDebugEnabled()) {
		log.debug(
			"findVerticalizzazioniparametriByModuloAndValue#Cerco Verticalizzazioniparametri per modulo : {}, valore parametro : {}, software {}",
			new Object[] { modulo, value, ORMHelper.getSoftware() });
	    }
	    ft = getFilterTableVerticalizzazioniparametriByModuloAndValue(modulo, value, ORMHelper.getSoftware());
	    listParametri = verticalizzazioniparametriDAO.findByFilterTable(ft);
	    if (!listParametri.isEmpty()) {
		verticalizzazioniparametri = listParametri.get(0);
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("Valore non trovato per il software {}", ORMHelper.getSoftware());
		    log.debug(
			    "findVerticalizzazioniparametriByModuloAndValue#Cerco Verticalizzazioniparametri per modulo : {}, valore parametro : {}, software {}",
			    new Object[] { modulo, value, WebConstants.SOFTWARE_TT });
		}
		ft = getFilterTableVerticalizzazioniparametriByModuloAndValue(modulo, value, WebConstants.SOFTWARE_TT);
		listParametri = verticalizzazioniparametriDAO.findByFilterTable(ft);
		if (!listParametri.isEmpty()) {
		    verticalizzazioniparametri = listParametri.get(0);
		} else {
		    log.debug("Valore non trovato per il software {}. Non esiste nessun parametro con valore {} per la verticalizzazione {}",
			    new Object[] { WebConstants.SOFTWARE_TT, value, modulo });
		}
	    }
	}
	return verticalizzazioniparametri;
    }

    /**
     * Ritorna una filter table che filtra per modulo,valore del parametro e software
     * 
     * @param modulo
     * @param value
     * @param codicesoftware
     * @return
     */
    private FilterTable getFilterTableVerticalizzazioniparametriByModuloAndValue(String modulo, String value, String codicesoftware) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("verticalizzazioniparametribase.id.modulo", modulo, String.class));
	if (value != null) {
	    fr.addFilterField(FilterUtils.equals("valore", value.trim(), String.class));
	} else {
	    fr.addFilterField(FilterUtils.equals("valore", null, String.class));
	}
	fr.addFilterField(FilterUtils.equals("codice", codicesoftware, "software", String.class));
	ft.addRestriction(fr);
	return ft;
    }

    private String getNodoKey(String idcomuneAlias, String software, String value) {

	return idcomuneAlias + "-" + software + "-" + value;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	nodiSTC = new HashMap<String, String>();
    }

    private boolean insertAllowed(Verticalizzazioniparametri entity) {

	boolean isInsert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	String codiceComune = null;
	if (entity.getComune() != null) {
	    if (StringUtils.isNotBlank(entity.getComune().getCodicecomune())) {
		codiceComune = entity.getComune().getCodicecomune();
	    }
	}
	List<Verticalizzazioniparametri> list = this.findByModuloAndIdcomuneAndSoftwareAndComune(
		entity.getVerticalizzazioniparametribase().getId().getModulo(), codiceComune, entity.getSoftware().getCodice());
	for (Verticalizzazioniparametri verticalizzazioniparametri : list) {
	    if (verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro()
		    .equals(entity.getVerticalizzazioniparametribase().getId().getParametro())) {
		_ivs.add(new InvalidValue("service_error.verticalizzazione_parametro_software_configurato", null, null, null, null));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return isInsert;
    }

    @Override
    public List<Verticalizzazioniparametri> findParametriConfiguratiByModulo(String modulo) {

	return findParametriConfiguratiByModuloAndComune(modulo, null);
    }

    @Override
    public List<Verticalizzazioniparametri> findParametriConfiguratiByModuloAndComune(String modulo, String codiceComune) {

	List<Verticalizzazioniparametri> verticalizzazioniparametris = new ArrayList<Verticalizzazioniparametri>();
	Map<String, Verticalizzazioniparametri> mapParametri = new HashMap<String, Verticalizzazioniparametri>();
	List<Verticalizzazioniparametri> pSoftware = verticalizzazioniparametriDAO.findByModuloAndIdcomuneAndSoftwareAndComune(modulo,
		ORMHelper.getIdcomune(), ORMHelper.getSoftware(), codiceComune);
	for (Verticalizzazioniparametri verticalizzazioniparametri : pSoftware) {
	    mapParametri.put(verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro(), verticalizzazioniparametri);
	}
	Collection<Verticalizzazioniparametri> cMap = mapParametri.values();
	for (Verticalizzazioniparametri verticalizzazioniparametri : cMap) {
	    verticalizzazioniparametris.add(verticalizzazioniparametri);
	}
	return verticalizzazioniparametris;
    }

    @Override
    public List<ChiaveValoreBean<Comuni, Software>> findComuniESoftware(String modulo, String... parametro) {

	return verticalizzazioniparametriDAO.findComuniESoftware(modulo, parametro);
    }

    @Override
    public Verticalizzazioniparametri findByModuloAndParametroAndSoftwareAndComune(String modulo, String parametro, String software,
	    String codiceComune) {

	DetachedCriteria det = DetachedCriteria.forClass(Verticalizzazioniparametri.class);
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	if (StringUtils.isBlank(software)) {
	    det.add(Restrictions.in("software.codice", new Object[] { "TT", ORMHelper.getSoftware() }));
	} else {
	    det.add(Restrictions.in("software.codice", new Object[] { "TT", software }));
	}
	det.add(Restrictions.eq("verticalizzazioniparametribase.id.modulo", modulo));
	det.add(Restrictions.eq("verticalizzazioniparametribase.id.parametro", parametro));
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
	//2. Escludo i parametri delle verticalizzazioni disabilitate
	List<Verticalizzazioniparametri> parametri = this.verticalizzazioniparametriDAO.findByCriteria(det);
	List<Verticalizzazioniparametri> list = new ArrayList<Verticalizzazioniparametri>(0);
	for (Verticalizzazioniparametri par : parametri) {
	    String parModulo = par.getVerticalizzazioniparametribase().getId().getModulo();
	    String parSoftware = par.getSoftware().getCodice();
	    String parCodiceComune = par.getComune() != null ? par.getComune().getCodicecomune() : null;
	    if (this.verticalizzazioniService.isAttivaPerComuneESoftware(parModulo, parSoftware, parCodiceComune)) {
		list.add(par);
	    }
	}
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
    public List<String> findValoreByModuloEParametro(String modulo, String parametro) {

	List<String> valore = new ArrayList<String>();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.modulo", modulo, "verticalizzazioniparametribase", String.class));
	if (StringUtils.isBlank(parametro)) {
	    FilterRestriction par = new FilterRestriction();
	    par.setAndOrRestriction(AndOrRestriction.OR);
	    par.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	    par.addFilterField(FilterUtils.isNull("id.parametro", "verticalizzazioniparametribase"));
	    fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	} else {
	    fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	}
	if (!ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    fr.addFilterField(FilterUtils.in("codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, "software", String.class));
	}
	ft.addRestriction(fr);
	List<Verticalizzazioniparametri> list = verticalizzazioniparametriDAO.findByFilterTable(ft);
	for (Verticalizzazioniparametri vp : list) {
	    valore.add(vp.getValore());
	}
	return new ArrayList<String>(new LinkedHashSet<String>(valore));
    }

    @Override
    public List<CodiceDescrizioneBean> findComuniPerRegolaEParametro(String modulo, String parametro) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.modulo", modulo, "verticalizzazioniparametribase", String.class));
	fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	fr.addFilterField(FilterUtils.in("software.codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, String.class));
	ft.addRestriction(fr);
	List<Verticalizzazioniparametri> parametri = verticalizzazioniparametriDAO.findByFilterTable(ft);
	List<CodiceDescrizioneBean> ret = new ArrayList<CodiceDescrizioneBean>();
	for (Verticalizzazioniparametri vp : parametri) {
	    String codiceComune = vp.getSoftware().getCodice() + "_";
	    String comune = null;
	    if (vp.getComune() != null) {
		codiceComune += vp.getComune().getCodicecomune();
		comune = vp.getComune().getComune();
	    } else {
		codiceComune += "TUTTI";
		comune = "Tutti i comuni";
	    }
	    ret.add(new CodiceDescrizioneBean(codiceComune, comune));
	}
	return ret;
    }

    @Override
    public List<Verticalizzazioniparametri> findConfigurazioniAttive(String modulo, String parametro) {

	//1. Estrapolo tutti i potenziali protocolli configurati
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.modulo", modulo, "verticalizzazioniparametribase", String.class));
	if (StringUtils.isBlank(parametro)) {
	    FilterRestriction par = new FilterRestriction();
	    par.setAndOrRestriction(AndOrRestriction.OR);
	    par.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	    par.addFilterField(FilterUtils.isNull("id.parametro", "verticalizzazioniparametribase"));
	    fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	} else {
	    fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	}
	if (!ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    fr.addFilterField(FilterUtils.in("codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, "software", String.class));
	}
	ft.addRestriction(fr);
	//2. Escludo i parametri delle verticalizzazioni disabilitate
	List<Verticalizzazioniparametri> list = new ArrayList<Verticalizzazioniparametri>(0);
	for (Verticalizzazioniparametri par : verticalizzazioniparametriDAO.findByFilterTable(ft)) {
	    String parModulo = par.getVerticalizzazioniparametribase().getId().getModulo();
	    String parSoftware = par.getSoftware().getCodice();
	    String parCodiceComune = par.getComune() != null ? par.getComune().getCodicecomune() : null;
	    if (this.verticalizzazioniService.isAttivaPerComuneESoftware(parModulo, parSoftware, parCodiceComune)) {
		list.add(par);
	    }
	}
	return list;
    }

    @Override
    public boolean checkConfigurazioneMultiplaPerSoftware(String modulo, String parametro) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.modulo", modulo, "verticalizzazioniparametribase", String.class));
	fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	ft.addRestriction(fr);
	List<Verticalizzazioniparametri> list = verticalizzazioniparametriDAO.findByFilterTable(ft, 0, 10);
	Set<String> softwareConfigurati = new HashSet<String>();
	for (Verticalizzazioniparametri vp : list) {
	    String software = vp.getSoftware().getCodice();
	    softwareConfigurati.add(software);
	}
	return softwareConfigurati.size() > 1;
    }

    @Override
    public boolean checkConfigurazioneMultiplaPerComune(String modulo, String parametro) {

	if (!comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune())) {
	    return false;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.modulo", modulo, "verticalizzazioniparametribase", String.class));
	fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	ft.addRestriction(fr);
	List<Verticalizzazioniparametri> list = verticalizzazioniparametriDAO.findByFilterTable(ft, 0, 10);
	Set<String> comuni = new HashSet<String>();
	for (Verticalizzazioniparametri vp : list) {
	    String codiceComune = vp.getComune() == null ? "TUTTI" : vp.getComune().getCodicecomune();
	    comuni.add(codiceComune);
	}
	return comuni.size() > 1;
    }

    @Override
    public void updateParametroDelModulo(String modulo, String parametro, String comune, String nuovoValore) {

	List<Verticalizzazioniparametri> list = trovaVP(modulo, parametro, comune, ORMHelper.getSoftware());
	Verticalizzazioniparametri daAggiornare = null;
	for (Verticalizzazioniparametri vp : list) {
	    daAggiornare = vp;
	    if (vp.getComune() != null) {
		break;
	    }
	}
	if (daAggiornare != null) {
	    daAggiornare.setValore(nuovoValore);
	    update(daAggiornare);
	    return;
	}
	list = trovaVP(modulo, parametro, comune, WebConstants.SOFTWARE_TT);
	for (Verticalizzazioniparametri vp : list) {
	    daAggiornare = vp;
	    if (vp.getComune() != null) {
		break;
	    }
	}
	if (daAggiornare != null) {
	    daAggiornare.setValore(nuovoValore);
	    update(daAggiornare);
	    return;
	}
	throw new RuntimeException("Parametro " + parametro + " del modulo " + ORMHelper.getSoftware() + " e comune " + comune + " non configurato");
    }

    private List<Verticalizzazioniparametri> trovaVP(String modulo, String parametro, String comune, String software) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.modulo", modulo, "verticalizzazioniparametribase", String.class));
	fr.addFilterField(FilterUtils.equals("id.parametro", parametro, "verticalizzazioniparametribase", String.class));
	fr.addFilterField(FilterUtils.equals("software.codice", software, String.class));
	ft.addRestriction(fr);
	FilterRestriction fr2 = new FilterRestriction();
	fr2.setAndOrRestriction(AndOrRestriction.OR);
	fr2.addFilterField(FilterUtils.equals("comune.codicecomune", comune, String.class));
	fr2.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	ft.addRestriction(fr2);
	return verticalizzazioniparametriDAO.findByFilterTable(ft);
    }

    @Override
    public List<Verticalizzazioniparametri> findParametriByModulo(String modulo, String... parametro) {

	return this.verticalizzazioniparametriDAO.findParametriByModulo(modulo, parametro);
    }
}
