package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniparametriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.SoftwareattiviId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniparametriHelper;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class VerticalizzazioniparametriServiceImpl extends BaseServiceImpl<Verticalizzazioniparametri, PkId> implements
	VerticalizzazioniparametriService {

    private static Logger log = LoggerFactory.getLogger(VerticalizzazioniparametriServiceImpl.class);
    private ComuniService comuniService;
    private VerticalizzazioniparametriDAO verticalizzazioniparametriDAO;
    private VerticalizzazioniService verticalizzazioniService;
    private SoftwareattiviService softwareattiviService;
    private SoftwareService softwareService;
    private ResponsabiliService responsabiliService;
    private UserSecurityService userSecurityService;
    private Map<String, String> nodiSTC;

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
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
	    List<Verticalizzazioniparametri> listTemp = this.findByModuloAndIdcomuneAndSoftwareAndComune(modulo, codiceComune, softwareattivi.getId()
		    .getFkSoftware());
	    //FIXME perdo ordinamento
	    List<Verticalizzazioniparametri> list = setFlagSoftwarePerAbilitatotransiet(listTemp);
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
	    List<Verticalizzazioniparametri> listTemp = this.findByModuloAndIdcomuneAndSoftwareAndComune(modulo, comune, softwareattivi.getSoftware()
		    .getCodice());
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

	//	DetachedCriteria criteria = getIdcomuneCriteria();
	//	criteria.add(Restrictions.eq("id.modulo", modulo));
	//	criteria.createAlias("software", "_software");
	//	criteria.add(Restrictions.eq("_software.codice", software));
	//	criteria.add(Restrictions.eq("id.modulo", modulo));
	//	criteria.addOrder(Order.asc("_software.ordine"));
	//	criteria.addOrder(Order.asc("id.parametro"));
	//	@SuppressWarnings("unchecked")
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

    //    @Override
    //    public List<VerticalizzazioniparametriHelper> findVerticalizzazioniparametriHelperAndCheckConfigurabilePerOperatore(String modulo,
    //	    String codicecomune, String software) {
    //
    //	List<VerticalizzazioniparametriHelper> listParametri = new ArrayList<VerticalizzazioniparametriHelper>();
    //	List<Softwareattivi> softwareattiviList = softwareattiviService.findAll(null, null);
    //	// per ogni software attivo, per il comune trovo la lista dei parametri della verticalizzazione
    //	for (Softwareattivi softwareattivi : softwareattiviList) {
    //	    List<Verticalizzazioniparametri> listTemp = this.findByModuloAndIdcomuneAndSoftware(modulo, idcomune, softwareattivi.getSoftware()
    //		    .getCodice());
    //	    //FIXME perdo ordinamento
    //	    List<Verticalizzazioniparametri> list = setFlagSoftwarePerAbilitatotransiet(listTemp);
    //	    VerticalizzazioniparametriHelper verticalizzazioniparametriHelper = null;
    //	    if (!list.isEmpty()) {
    //		verticalizzazioniparametriHelper = new VerticalizzazioniparametriHelper();
    //		verticalizzazioniparametriHelper.setSoftware(softwareattivi.getSoftware());
    //		verticalizzazioniparametriHelper.setVerticalizzazioniparametris(list);
    //	    }
    //	    if (verticalizzazioniparametriHelper != null)
    //		listParametri.add(verticalizzazioniparametriHelper);
    //	}
    //	return listParametri;
    //    }
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
	    Verticalizzazioniparametri verticalizzazioniparametri = this.findVerticalizzazioniparametriByModuloAndValue(
		    WebConstants.VERTICALIZZAZIONE_STC, value);
	    // Controllo se esiste questo parametro
	    if (verticalizzazioniparametri != null) {
		// Controllo se il parametro trovato è per software TT
		if (verticalizzazioniparametri.getSoftware().getCodice().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
		    if (log.isDebugEnabled()) {
			log.debug("findNomeNodoModuloSTC#Valore trovato è per il software {}, controllo se questo valore è già sulla mappa",
				WebConstants.SOFTWARE_TT);
		    }
		    // Creo una nuova chiave con software TT e controllo se esiste una valore sulla mappa per questa chiave
		    String keyTT = getNodoKey(ORMHelper.getIdcomuneAlias(), WebConstants.SOFTWARE_TT, value);
		    if (nodiSTC.get(keyTT) == null) {
			// Non c'è, lo metto sulla mappa e associo al nodo questo valore
			nodiSTC.put(keyTT, verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro());
			idnodo = verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro();
		    } else {
			// L'ho trovata ,associo al nod il valore torvato
			idnodo = nodiSTC.get(keyTT);
		    }
		} else {// Il valore trovato non è il per TT, ma per software corrente
			//Sicuramente non è contenuto nella mappa (controllo iniziale), lo setto nella mappa 
			// e alla variabile idnodo.
		    nodiSTC.put(key, verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro());
		    idnodo = verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro();
		}
	    } else {
		// Non ho trovato nessun valore, ritorno servizio come stringa vuota
		if (log.isDebugEnabled()) {
		    log.debug("findNomeNodoModuloSTC#Parametro non trovato per la verticalizzazione {} e  valore {}", new Object[] {
			    WebConstants.VERTICALIZZAZIONE_STC, value });
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
    public String decodeNomeNodoModuloSTC(String codiceNodo, String idMittente) {

	if (log.isDebugEnabled()) {
	    log.debug("decodeNomeNodoModuloSTC#Inizio decodifica nome servizio");
	}
	// Recupero il nome del nodo apartire dal codice (ES 1200-->NLA_IDNODO_AREARISERVATA)
	String nomeNodo = this.findNomeNodoModuloSTC(codiceNodo);
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
		    Software software = softwareService.findById(idMittente);
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
	List<Verticalizzazioniparametri> list = this.findByModuloAndIdcomuneAndSoftwareAndComune(entity.getVerticalizzazioniparametribase().getId()
		.getModulo(), codiceComune, entity.getSoftware().getCodice());
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
	//	List<Verticalizzazioniparametri> pSoftwareTT = verticalizzazioniparametriDAO.findByModuloAndIdcomuneAndSoftwareAndComune(modulo,
	//		ORMHelper.getIdcomune(), WebConstants.SOFTWARE_TT, codiceComune);
	//	for (Verticalizzazioniparametri verticalizzazioniparametri : pSoftwareTT) {
	//	    mapParametri.put(verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro(), verticalizzazioniparametri);
	//	}
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
}
