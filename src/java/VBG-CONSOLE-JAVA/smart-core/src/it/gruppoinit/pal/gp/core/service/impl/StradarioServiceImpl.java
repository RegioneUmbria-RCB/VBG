package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.StradarioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.helper.AllineamentoStradarioHelper;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.StradarioService;

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

/**
 * @author Luca Proietti
 * 
 */
@Service
public class StradarioServiceImpl extends BaseServiceImpl<Stradario, PkId> implements StradarioService {

    private static final Logger log = LoggerFactory.getLogger(StradarioServiceImpl.class.getName());
    private StradarioDAO stradarioDAO;
    private ComuniService comuniService;
    private ComuniassociatiService comuniassociatiService;

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

    @Override
    protected Class<Stradario> getEntityClass() {

	return Stradario.class;
    }

    @Override
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, Integer firstResult, Integer maxResult) {

	List<Responsabilicomuni> comuniList = comuniassociatiService.checkComuniAbilitatiPerResponsabile();
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

	List<Responsabilicomuni> listaComuni = comuniassociatiService.checkComuniAbilitatiPerResponsabile();
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
	    stradarioDAO.insert(entity);
	}
    }

    @Override
    public void update(Stradario entity) {

	if (validateEntity(entity)) {
	    stradarioDAO.update(entity);
	}
    }

    private List<Stradario> findByComuniAbilitati(List<Responsabilicomuni> comuniList) {

	if (comuniList == null || comuniList.size() == 0) {
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
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	int countRecordAreaDettagli = areedettagliService.countRecordByStradario(entity);
	//	if (countRecordAreaDettagli > 0) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "AREEDETTAGLI", null));
	//	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public Stradario bindDomainObject(Stradario entity, Class<?> idClass, String idPath) {

	Stradario stradario = super.bindDomainObject(entity, idClass, idPath);
	return afterBindDomainObject(stradario, entity);
    }

    private Stradario afterBindDomainObject(Stradario oggFromBind, Stradario entity) {

	//	if (rule != null) {
	//	    if (oggFromBind != null) {
	//		// aggiorno i campi dell'oggetto recuperato da DB se specificato da configurazione
	//	    } else {
	//		// inserisco la entity su DB se specificato da configurazione
	//	    }
	//	}
	return oggFromBind;
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
		if (list.size() == 0) {
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
	    if (comuniassociatis.size() > 0) {
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
	}
	return null;
    }

    @Override
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, Integer firstResult, Integer maxResult,
	    boolean searchDisabilitati) {

	List<Responsabilicomuni> comuniList = comuniassociatiService.checkComuniAbilitatiPerResponsabile();
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
	if (criteriRicerca.size() == 0) {
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
	if (result.size() == 0) {
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

    private Map<String, Set<String>> LISTA_TOPONIMI_PER_SESSION_FACTORY = new HashMap<String, Set<String>>();

    private Set<String> getListaToponimi(String sessionFactoryKey) {

	Set<String> result = LISTA_TOPONIMI_PER_SESSION_FACTORY.get(sessionFactoryKey);
	if (result == null) {
	    result = findToponimiPerInstallazione();
	    LISTA_TOPONIMI_PER_SESSION_FACTORY.put(sessionFactoryKey, result);
	} else {
	    if (result.size() == 0) {
		result = findToponimiPerInstallazione();
		LISTA_TOPONIMI_PER_SESSION_FACTORY.put(sessionFactoryKey, result);
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

	LISTA_TOPONIMI_PER_SESSION_FACTORY = new HashMap<String, Set<String>>();
    }

    @Override
    public Map<String, Map<String, AllineamentoStradarioHelper>> updateAllineaStradario(List<String> codiciComuni, Responsabili responsabile) {

	return null;
    }

    @Override
    public Stradario findByCodiceViario(String codiceViario, String codiceComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codviario", codiceViario, String.class));
	fr.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	ft.addRestriction(fr);
	List<Stradario> list = stradarioDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }
}
