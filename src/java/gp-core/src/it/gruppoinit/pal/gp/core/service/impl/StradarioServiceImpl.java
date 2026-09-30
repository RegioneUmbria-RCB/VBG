package it.gruppoinit.pal.gp.core.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.StradarioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;
import it.gruppoinit.pal.gp.core.domain.helper.AllineamentoStradarioHelper;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AreedettagliService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.SitService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.StradariozoneService;
import it.gruppoinit.pal.gp.core.service.helper.StradarioDTO;
import it.gruppoinit.pal.gp.core.utils.LoggerAllineamentostradario;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.FiltroRicercaListaVie;

/**
 * @author Luca Proietti
 * 
 */
@Service
public class StradarioServiceImpl extends BaseServiceImpl<Stradario, PkId> implements StradarioService {

    private Map<String, Set<String>> listaToponimiPerSessionFactory = new HashMap<String, Set<String>>();
    private static final Logger log = LoggerFactory.getLogger(StradarioServiceImpl.class.getName());
    private StradarioDAO stradarioDAO;
    private ComuniService comuniService;
    private ComuniassociatiService comuniassociatiService;
    private IstanzestradarioService istanzestradarioService;
    private AreedettagliService areedettagliService;
    private SitService sitService;
    private StradariozoneService stradariozoneService;

    @Autowired
    public void setStradarioDAO(StradarioDAO stradarioDAO) {

	this.stradarioDAO = stradarioDAO;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Autowired
    public void setAreedettagliService(AreedettagliService areedettagliService) {

	this.areedettagliService = areedettagliService;
    }

    @Autowired
    public void setSitService(SitService sitService) {

	this.sitService = sitService;
    }

    @Autowired
    public void setStradariozoneService(StradariozoneService stradariozoneService) {

	this.stradariozoneService = stradariozoneService;
    }

    @Override
    protected Class<Stradario> getEntityClass() {

	return Stradario.class;
    }

    @Override
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, Integer firstResult, Integer maxResult) {

	List<Responsabilicomuni> comuniList = comuniassociatiService.checkComuniAbilitatiPerResponsabile(true);
	String[] codiciComune = responsabiliComuniToCodici(comuniList);
	return stradarioDAO.findByDescrizione(descrizione, codiceComune, codiciComune, firstResult, maxResult);
    }

    @Override
    public void delete(Stradario entity) {

	if (isDeleteAllowed(entity)) {
	    // con la cascade rimuove anche i record in AREEDETTAGLI
	    stradarioDAO.delete(entity);
	}
    }

    @Override
    public List<Stradario> findAll(Integer firstResult, Integer maxResult) {

	List<Responsabilicomuni> listaComuni = comuniassociatiService.checkComuniAbilitatiPerResponsabile(true);
	List<Stradario> stradarioList = null;
	if (listaComuni.isEmpty()) {
	    stradarioList = stradarioDAO.findAll(firstResult, maxResult);
	} else {
	    stradarioList = this.findByComuniAbilitati(listaComuni);
	}
	return stradarioList;
    }

    @Override
    public Stradario findById(PkId id) {

	return stradarioDAO.findById(id);
    }

    @Override
    public void insert(Stradario entity) {

	if (validateEntity(entity)) {
	    dataIntegration(entity);
	    stradarioDAO.insert(entity);
	}
    }

    @Override
    public void update(Stradario entity) {

	if (validateEntity(entity)) {
	    dataIntegration(entity);
	    stradarioDAO.update(entity);
	}
    }

    private List<Stradario> findByComuniAbilitati(List<Responsabilicomuni> comuniList) {

	if (comuniList == null || comuniList.isEmpty()) {
	    return null;
	}
	String[] codiciComune = responsabiliComuniToCodici(comuniList);
	return stradarioDAO.findAllByCodiciComuni(codiciComune);
    }

    @Override
    public List<Stradario> findByMercatoAndDescrizione(Integer codicemercato, String descrizione) {

	return stradarioDAO.findByMercatoAndDescrizione(codicemercato, descrizione);
    }

    @Override
    public List<Stradario> findByMercatoAndDescrizione(Integer codicemercato, String descrizione, boolean searchDisabilitati) {

	return stradarioDAO.findByMercatoAndDescrizione(codicemercato, descrizione, searchDisabilitati);
    }

    private String[] responsabiliComuniToCodici(List<Responsabilicomuni> comuniList) {

	String[] codiciComune = new String[comuniList.size()];
	for (int i = 0; i < comuniList.size(); i++) {
	    codiciComune[i] = comuniList.get(i).getComune().getCodicecomune();
	}
	return codiciComune;
    }

    @Override
    protected boolean isDeleteAllowed(Stradario entity) {

	boolean delete = true;
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	int countRecordStradario = istanzestradarioService.countRecordByStradario(entity);
	if (countRecordStradario > 0) {
	    ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZESTRADARIO", null));
	}
	int countRecordAreaDettagli = areedettagliService.countRecordByStradario(entity);
	if (countRecordAreaDettagli > 0) {
	    ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "AREEDETTAGLI", null));
	}
	if (!entity.getMercatistradarios().isEmpty()) {
	    ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATISTRADARIO", null));
	}
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	return delete;
    }

    @Override
    protected Stradario customBindDomainObject(Stradario entity) {

	if (entity == null) {
	    return null;
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	if (StringUtils.isNotBlank(entity.getCodviario())) {
	    FilterRestriction filterRestriction = new FilterRestriction();
	    filterRestriction.addFilterField(FilterUtils.equals("codviario", entity.getCodviario(), String.class));
	    filterTable.addRestriction(filterRestriction);
	    List<Stradario> list = stradarioDAO.findByFilterTable(filterTable);
	    if (list.size() == 1) {
		return list.get(0);
	    } else {
		if (list.isEmpty()) {
		    throw new RuntimeException("Nessun stradario trovato per il codice viario [" + entity.getCodviario() + "]");
		}
		if (list.size() > 1) {
		    throw new RuntimeException("Sono stati trovati più stradari per il codice viario [" + entity.getCodviario() + "]");
		}
	    }
	}
	filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	boolean almenoUno = false;
	if (StringUtils.isNotBlank(entity.getDescrizione())) {
	    FilterRestriction filterRestriction = new FilterRestriction();
	    filterRestriction.addFilterField(FilterUtils.equalsIgnoreCase("descrizione", entity.getDescrizione()));
	    filterTable.addRestriction(filterRestriction);
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank(entity.getPrefisso())) {
	    FilterRestriction filterRestriction = new FilterRestriction();
	    filterRestriction.addFilterField(FilterUtils.equalsIgnoreCase("prefisso", entity.getPrefisso()));
	    filterTable.addRestriction(filterRestriction);
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank(entity.getCap())) {
	    FilterRestriction filterRestriction = new FilterRestriction();
	    filterRestriction.addFilterField(FilterUtils.equalsIgnoreCase("cap", entity.getCap()));
	    filterTable.addRestriction(filterRestriction);
	    almenoUno = true;
	}
	if (StringUtils.isNotBlank(entity.getLocfraz())) {
	    FilterRestriction filterRestriction = new FilterRestriction();
	    filterRestriction.addFilterField(FilterUtils.equalsIgnoreCase("locfraz", entity.getLocfraz()));
	    filterTable.addRestriction(filterRestriction);
	    almenoUno = true;
	}
	if (entity.getComune() != null && StringUtils.isNotBlank(entity.getComune().getCodicecomune())) {
	    FilterRestriction filterRestriction = new FilterRestriction();
	    filterRestriction.addFilterField(FilterUtils.equalsIgnoreCase("comune.codicecomune", entity.getComune().getCodicecomune()));
	    filterTable.addRestriction(filterRestriction);
	    almenoUno = true;
	}
	if (almenoUno) {
	    List<Stradario> list = stradarioDAO.findByFilterTable(filterTable);
	    if (list.size() == 1) {
		return list.get(0);
	    }
	}
	if (StringUtils.isNotBlank(entity.getDescrizione())) {
	    String codiceComune = "";
	    if (entity.getComune() != null) {
		codiceComune = entity.getComune().getCodicecomune();
	    }
	    List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	    String[] codiciComunilist = null;
	    if (!comuniassociatis.isEmpty()) {
		codiciComunilist = new String[comuniassociatis.size()];
		int i = 0;
		for (Comuniassociati comuniassociati : comuniassociatis) {
		    codiciComunilist[i] = comuniassociati.getComune().getCodicecomune();
		    i++;
		}
	    }
	    List<Stradario> stradarios = stradarioDAO.findByDescrizioneEsatta(entity.getDescrizione(), codiceComune, codiciComunilist, 0, 1);
	    if (stradarios.size() == 1) {
		return stradarios.get(0);
	    }
	    if (stradarios.size() > 1) {
		throw new RuntimeException("Sono stati trovati più stradari per la denominazione [" + entity.getDescrizione() + "]");
	    }
	    List<Stradario> strads = findByMatchParziale(entity.getDescrizione(), codiceComune, null, null);
	    if (strads.size() == 1) {
		return strads.get(0);
	    }
	}
	return null;
    }

    @Override
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, Integer firstResult, Integer maxResult,
	    boolean searchDisabilitati) {

	List<Responsabilicomuni> comuniList = comuniassociatiService.checkComuniAbilitatiPerResponsabile(true);
	String[] codiciComune = responsabiliComuniToCodici(comuniList);
	return stradarioDAO.findByDescrizione(descrizione, codiceComune, codiciComune, firstResult, maxResult, searchDisabilitati);
    }

    @Override
    public List<Stradario> findByMatchParziale(String descrizione, String codiceComune, Integer firstResult, Integer maxResult) {

	if (StringUtils.isBlank(descrizione)) {
	    return new ArrayList<Stradario>();
	}
	String[] splitDescr = descrizione.toLowerCase().split(" ");
	Set<String> listaToponimi = this.getListaToponimi(ORMHelper.getHibernateSFKeyUrl());
	if (splitDescr.length == 0) {
	    return new ArrayList<Stradario>();
	}
	List<String> criteriRicerca = new ArrayList<String>();
	for (int i = 0; i < splitDescr.length; i++) {
	    String s = splitDescr[i];
	    if (i == 0) {
		if (!listaToponimi.contains(s)) {
		    criteriRicerca.add(s);
		}
	    } else {
		criteriRicerca.add(s);
	    }
	}
	if (criteriRicerca.isEmpty()) {
	    return new ArrayList<Stradario>();
	}
	FilterTable ft = getBaseRicercaFrontend(codiceComune);
	FilterRestriction andRest = new FilterRestriction();
	andRest.setAndOrRestriction(AndOrRestriction.AND);
	FilterRestriction orRest = new FilterRestriction();
	orRest.setAndOrRestriction(AndOrRestriction.OR);
	for (String valore : criteriRicerca) {
	    andRest.addFilterField(FilterUtils.like("descrizione", "%" + valore + "%"));
	    orRest.addFilterField(FilterUtils.like("descrizione", "%" + valore + "%"));
	}
	ft.addRestriction(andRest);
	List<Stradario> result = stradarioDAO.findByFilterTable(ft, firstResult, maxResult);
	if (result.isEmpty()) {
	    ft = getBaseRicercaFrontend(codiceComune);
	    ft.addRestriction(orRest);
	    result = stradarioDAO.findByFilterTable(ft, firstResult, maxResult);
	}
	return result;
    }

    private FilterTable getBaseRicercaFrontend(String codiceComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	// codicecomune
	if (StringUtils.isNotBlank(codiceComune)) {
	    FilterRestriction codComune = new FilterRestriction();
	    codComune.setAndOrRestriction(AndOrRestriction.OR);
	    codComune.addFilterField(FilterUtils.isNull("codicecomune", "comune"));
	    codComune.addFilterField(FilterUtils.equals("codicecomune", codiceComune, "comune", Comuni.class));
	    ft.addRestriction(codComune);
	}
	// data
	FilterRestriction data = new FilterRestriction();
	data.setAndOrRestriction(AndOrRestriction.OR);
	data.addFilterField(FilterUtils.isNull("datavalidita"));
	data.addFilterField(FilterUtils.greaterEqual("datavalidita", Calendar.getInstance().getTime(), Date.class));
	ft.addRestriction(data);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return ft;
    }

    private Set<String> getListaToponimi(String sessionFactoryKey) {

	Set<String> result = this.listaToponimiPerSessionFactory.get(sessionFactoryKey);
	if (result == null) {
	    result = findToponimiPerInstallazione();
	    this.listaToponimiPerSessionFactory.put(sessionFactoryKey, result);
	} else {
	    if (result.isEmpty()) {
		result = findToponimiPerInstallazione();
		this.listaToponimiPerSessionFactory.put(sessionFactoryKey, result);
	    }
	}
	return result;
    }

    private Set<String> findToponimiPerInstallazione() {

	List<String> listaToponimi = stradarioDAO.findToponimiRaggruppati();
	Set<String> result = new TreeSet<String>();
	for (String toponimo : listaToponimi) {
	    toponimo = StringUtils.defaultIfEmpty(toponimo, "").trim();
	    if (StringUtils.isNotBlank(toponimo)) {
		result.add(toponimo.toLowerCase());
	    }
	}
	return result;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.listaToponimiPerSessionFactory = new HashMap<String, Set<String>>();
    }

    @Override
    public Map<String, Map<String, AllineamentoStradarioHelper>> updateAllineaStradario(Set<String> codiciComuni, Responsabili responsabile) {

	// Mappa che conterrà le strutture per visualizzare il risultato.
	Map<String, Map<String, AllineamentoStradarioHelper>> risultatoMap = new HashMap<String, Map<String, AllineamentoStradarioHelper>>();
	Map<String, AllineamentoStradarioHelper> allineamentoStradarioHelpers = new HashMap<String, AllineamentoStradarioHelper>();
	// Struttura che contiene il riepilogo dell'aggiornamento per ogni comune
	AllineamentoStradarioHelper allineamentoStradarioHelper = null;
	// Definizione delle variabili per la gestione della visualizzazione del riepilogo di ogni singolo 
	// comune
	int aggiornato = 0;
	int inserito = 0;
	StringBuilder errore;
	List<Comuni> listComuni = new ArrayList<Comuni>();
	List<String> errori = new ArrayList<String>();
	String codViario = "";
	// Per ogni singolo comune vado a fare l'aggiornamento
	for (String codCom : codiciComuni) {
	    Comuni comune = comuniService.findById(codCom);
	    listComuni.add(comune);
	    try {
		// Istanzio le variabili per la gestione della visualizzazione del riepilogo di ogni singolo 
		// comune
		aggiornato = 0;
		inserito = 0;
		errori = new ArrayList<String>();
		allineamentoStradarioHelper = new AllineamentoStradarioHelper();
		allineamentoStradarioHelper.setComune(comune.getComune());
		allineamentoStradarioHelper.setCodicecomune(comune.getCodicecomune());
		log.debug("updateAllineaStradario#Inizio Aggiornamento dello stradario per il comune : {}..........", codCom);
		List<String> list = new ArrayList<String>();
		list.add(codCom);
		log.debug("updateAllineaStradario#Invoco il ws getListaVie per il comune : {}..........", codCom);
		// Invoco la chimata che ritorna dal sit attivo la lista di tutte le vie per il comune passato 
		List<Stradario> stradarios = sitService.getListaVie(ORMHelper.getToken(), FiltroRicercaListaVie.Tutte, list);
		log.debug("updateAllineaStradario#Sono state trovate {} vie da verificare", stradarios.size());
		log.debug("updateAllineaStradario#Inizio a ciclare le vie per inserire/aggionare lo stradario per il comune : {}.....", codCom);
		// Clico tutti gli stradari ritornati da sit e controllo se già esiste uno stradario sul db filtrando per 
		// codicecomune e codiceviario
		// 1.Travato 	:aggiorno il record il db con i dati ritornati dal sit
		// 2.Non trovato:inserisco il record nel db
		for (Stradario stradario : stradarios) {
		    try {
			String codiceComune = null;
			codViario = stradario.getCodviario();
			if (stradario.getComune() != null && StringUtils.isNotBlank(stradario.getComune().getCodicecomune())) {
			    codiceComune = stradario.getComune().getCodicecomune();
			}
			Stradario stradarioDb = this.findByCodiceViario(codViario, codiceComune, true);
			if (stradarioDb != null) {
			    stradarioDb.setCodviario(stradario.getCodviario());
			    if (StringUtils.isNotBlank(stradarioDb.getPrefisso())) {
				if (StringUtils.isNotBlank(stradario.getPrefisso())) {
				    stradarioDb.setPrefisso(stradario.getPrefisso());
				}
			    } else {
				stradarioDb.setPrefisso(StringUtils.defaultString(stradario.getPrefisso(), "-"));
			    }
			    stradarioDb.setDescrizione(stradario.getDescrizione());
			    if (StringUtils.isNotBlank(stradario.getLocfraz())) {
				stradarioDb.setLocfraz(stradario.getLocfraz());
			    }
			    if (stradario.getDatavalidita() != null) {
				stradarioDb.setDatavalidita(stradario.getDatavalidita());
			    }
			    this.update(stradarioDb);
			    aggiornato++;
			} else {
			    this.insert(stradario);
			    inserito++;
			}
		    } catch (Exception e) {
			log.error("Errore durante l' inserimento/aggiornamento dello stradario codice {} per il comune {}-->{}",
				new Object[] { codViario, codCom, e });
			errore = new StringBuilder();
			errore = errore.append("Errore durante l' inserimento/aggiornamento dello stradario codice ").append(codViario)
				.append(" per il comune ").append(codCom).append(". Inserimento/Aggiornamento non eseguito <br />");
			allineamentoStradarioHelper.setIsErrore(true);
			errori.add(errore.toString());
		    }
		}
		stradarioDAO.flush();
		stradarioDAO.commit();
		log.debug("updateAllineaStradario#Fine Aggiornamento dello stradario per il comune : {}.............", codCom);
	    } catch (Exception e) {
		log.error("Errore durante la chiamata al ws per il codice comune {} : {}", codCom, e);
		errore = new StringBuilder();
		errore = errore.append("Errore durante la chiamata al ws per il codice comune : ").append(codCom)
			.append(". Aggiornamento non eseguito <br />");
		allineamentoStradarioHelper.setIsErrore(true);
		errori.add(errore.toString());
	    }
	    // Popolo la struttura utilizzata per la visualizzare del risultato dell'aggionamento dle singolo comune
	    allineamentoStradarioHelper.setNumAggiornati(aggiornato);
	    allineamentoStradarioHelper.setNumAggiunti(inserito);
	    allineamentoStradarioHelpers.put(allineamentoStradarioHelper.getCodicecomune(), allineamentoStradarioHelper);
	    allineamentoStradarioHelper.setErrori(errori);
	}
	risultatoMap.put("RISULTATO", allineamentoStradarioHelpers);
	// Popola il file di log che specifica che è stato fatto un aggiornamento dello stradario, se responsabile
	// è !=null è stato effettuato da un operatore, altrimenti è stata attivata la schedulazione
	if (responsabile != null) {
	    LoggerAllineamentostradario.logAllineamentoStradario(responsabile.getResponsabile(), listComuni, risultatoMap);
	} else {
	    LoggerAllineamentostradario.logAllineamentoStradario(listComuni, risultatoMap);
	}
	return risultatoMap;
    }

    @Override
    public Stradario findByCodiceViario(String codiceViario) {

	return findByCodiceViario(codiceViario, null, false);
    }

    @Override
    public List<Stradario> findListByCodiceViario(String codiceViario, String codiceComune, boolean isCodicecomuneObbligatorio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codviario", codiceViario, String.class));
	if (isCodicecomuneObbligatorio && StringUtils.isNotBlank(codiceComune)) {
	    fr.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	}
	ft.addRestriction(fr);
	return stradarioDAO.findByFilterTable(ft);
    }

    @Override
    public Stradario findByCodiceViario(String codiceViario, String codiceComune, boolean isCodicecomuneObbligatorio) {

	List<Stradario> list = this.findListByCodiceViario(codiceViario, codiceComune, isCodicecomuneObbligatorio);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    private void dataIntegration(Stradario entity) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Stradario entity) {

	Comuni comuni = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(comuni);
	Comuni comuniLocalizzazioni = comuniService.bindDomainObject(entity.getComuneLocalizzazione(), String.class, "codicecomune");
	entity.setComuneLocalizzazione(comuniLocalizzazioni);
	Stradariozone stradariozone = stradariozoneService.bindDomainObject(entity.getStradariozone(), PkId.class, "id.codice");
	entity.setStradariozone(stradariozone);
    }

    @Override
    public StradarioDTO stradarioToDTO(Stradario stradario) {

	stradario = this.findById(new PkId(stradario.getId().getCodice()));
	StradarioDTO res = new StradarioDTO();
	res.setId(stradario.getId());
	res.setCap(stradario.getCap());
	res.setCodviario(stradario.getCodviario());
	if (stradario.getComune() != null) {
	    Comuni comune = new Comuni();
	    comune.setCodicecomune(stradario.getComune().getCodicecomune());
	    comune.setComune(stradario.getComune().getComune());
	    comune.setProvincia(stradario.getComune().getProvincia());
	    comune.setSiglaprovincia(stradario.getComune().getSiglaprovincia());
	    res.setComune(comune);
	}
	if (stradario.getComuneLocalizzazione() != null) {
	    Comuni comune = new Comuni();
	    comune.setCodicecomune(stradario.getComuneLocalizzazione().getCodicecomune());
	    comune.setComune(stradario.getComuneLocalizzazione().getComune());
	    comune.setProvincia(stradario.getComuneLocalizzazione().getProvincia());
	    comune.setSiglaprovincia(stradario.getComuneLocalizzazione().getSiglaprovincia());
	    res.setComuneLocalizzazione(comune);
	}
	res.setDescrizione(stradario.getDescrizione());
	res.setPrefisso(stradario.getPrefisso());
	if (stradario.getStradariozone() != null) {
	    Stradariozone stradariozone = new Stradariozone();
	    stradariozone.setId(stradario.getStradariozone().getId());
	    stradariozone.setZona(stradario.getStradariozone().getZona());
	    res.setStradariozone(stradariozone);
	}
	return res;
    }

    @Override
    public List<StradarioDTO> findByMatchParzialeToDTO(String filtroDescrizione, String codiceComune, Integer firstResult, Integer maxResults) {

	List<StradarioDTO> dtos = new ArrayList<StradarioDTO>();
	List<Stradario> strads = this.findByMatchParziale(filtroDescrizione, codiceComune, firstResult, maxResults);
	for (Stradario stradario : strads) {
	    dtos.add(this.stradarioToDTO(stradario));
	}
	return dtos;
    }

    @Override
    public void disabilitaStradari(String codiceComune, Integer codiceStradarioIniziale) {

	this.stradarioDAO.disabilitaStradari(codiceComune, codiceStradarioIniziale);
    }
}
