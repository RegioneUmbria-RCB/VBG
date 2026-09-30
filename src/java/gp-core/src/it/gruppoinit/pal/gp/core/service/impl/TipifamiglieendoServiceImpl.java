package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipifamiglieendoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.helper.FlagPubblicaEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipifamiglieendoServiceImpl extends BaseServiceImpl<Tipifamiglieendo, PkId> implements TipifamiglieendoService {

    private TipifamiglieendoDAO tipifamiglieendoDAO;

    @Autowired
    public void setTipifamiglieendoDAO(TipifamiglieendoDAO tipifamiglieendoDAO) {

	this.tipifamiglieendoDAO = tipifamiglieendoDAO;
    }

    private InventarioprocedimentiService inventarioprocedimentiService;

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    private TipiendoService tipiendoService;

    @Autowired
    public void setTipiendoService(TipiendoService tipiendoService) {

	this.tipiendoService = tipiendoService;
    }

    @Override
    public void delete(Tipifamiglieendo entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	InvalidValue iv = null;
	int isUsedInTipiendo = tipiendoService.countByTipiFamiglieEndo(entity);
	if (isUsedInTipiendo > 0) {
	    iv = new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPIENDO", null);
	    _ivs.add(iv);
	    this.throwValidationMessages(_ivs);
	}
	tipifamiglieendoDAO.delete(entity);
	resetObjectCached();
    }

    @Override
    public List<Tipifamiglieendo> findAll(Integer firstResult, Integer maxResult) {

	return tipifamiglieendoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Tipifamiglieendo findById(PkId id) {

	return tipifamiglieendoDAO.findById(id);
    }

    @Override
    public void insert(Tipifamiglieendo entity) {

	if (validateEntity(entity)) {
	    dataIntegration(entity);
	    tipifamiglieendoDAO.insert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void update(Tipifamiglieendo entity) {

	if (validateEntity(entity)) {
	    dataIntegration(entity);
	    tipifamiglieendoDAO.update(entity);
	    resetObjectCached();
	}
    }

    @Override
    protected Class<Tipifamiglieendo> getEntityClass() {

	return Tipifamiglieendo.class;
    }

    @Override
    public List<Tipifamiglieendo> findByFilter(Tipifamiglieendo entity) {

	return tipifamiglieendoDAO.findByFilter(entity);
    }

    @Override
    public List<Tipifamiglieendo> findByDescSWeTT(String textToSearch) {

	return tipifamiglieendoDAO.findByDescSWeTT(textToSearch);
    }

    @Override
    public Set<Tipifamiglieendo> findAllBySoftwareAndTTAndEndoAttivabili(Istanze istanza, List<String> listaCodiciNature) {

	// Recupero tutte le famiglie endo per il software in esame e il softare TT
	// Se tipifamiglieendoEnum = WITH_ENDO_COLLEGATI verrà impostato un ulteriore filtro che 
	// farà restituire solo quelle che hanno almeno un endo procedimento collegato.
	// Il risultato sarà ordinato rispettivamente per ol campo ordine (ASC) e tipo (ASC)
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction basefilter = new FilterRestriction();
	basefilter.setAndOrRestriction(AndOrRestriction.OR);
	basefilter.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	filterTable.addRestriction(basefilter);
	basefilter.addFilterField(FilterUtils.equals("codice", WebConstants.SOFTWARE_TT, "software", String.class));
	// Applichiamo un filtro che mostra solo le famiglie con endo collegati. Il filtro verrà applicato solo 
	// se tipifamigliaendoEnum ha il valore WITH_ENDO_COLLEGATI
	FilterRestriction nessunEndo = new FilterRestriction();
	nessunEndo.addFilterField(FilterUtils.isNotEmpty("inventarioprocedimentis", "tipiendos"));
	filterTable.addRestriction(nessunEndo);
	filterTable.addOrder(FilterUtils.order("ordine", OrderTypeEnum.ASC));
	filterTable.addOrder(FilterUtils.order("tipo", OrderTypeEnum.ASC));
	// Scorro la lista della famiglie endo trovate per vedere quelle che effettivamente dovranno essere mostrate.
	// Creo una nuova lista ordinata di famiglie endo che conterrà solo quelle che hanno almeno un tipo endo configurato 
	// con un endoprocediemnto attivabile
	Set<Tipifamiglieendo> listTipiFamigliaEndo = new LinkedHashSet<Tipifamiglieendo>();
	List<Tipifamiglieendo> list = tipifamiglieendoDAO.findByFilterTable(filterTable);
	// contatore usato per verificare se per la famiglia endo in esame contiene almeno un tipo endo
	//con un endoprocediemnto attivabile
	int contatore = 0;
	for (Tipifamiglieendo tipifamiglieendo : list) {
	    // Recupero i tipi endo collegati alla famiglia passata
	    List<Tipiendo> listTipiEndo = tipiendoService.findByTipiFamiglieEndo(tipifamiglieendo);
	    Set<Tipiendo> listCategoriaEndo = new LinkedHashSet<Tipiendo>();
	    // per ogni categoria endo controllo che esista un endo attivabile.
	    // Un endo sarà attivabile per l'istanza se non è già stato attivato.
	    // Inoltre gli endo saranno filtrati ti per una o più nauture endo (Messe in condizione di OR tra loro)
	    for (Tipiendo tipiendo : listTipiEndo) {
		// ritorna il numero di endo attivabile
		Integer count = inventarioprocedimentiService.countByTipiendoAndNonAttivatiPerIstanza(tipiendo, null, istanza, listaCodiciNature,
			null);
		// se cout >0 allora alla  lista di famiglie endo associo il tipo endo 
		if (count > 0) {
		    listCategoriaEndo.add(tipiendo);
		    contatore++;
		}
	    }
	    // se contatore è > 0 allora significa che per la famiglia endo in esame esiste almeno un tipo endo con un endo
	    // procedimento attivabile, allora  setto la famiglia endo alla lista che verrà restituita dal metodo.
	    if (contatore > 0) {
		tipifamiglieendo.setTipiendos(listCategoriaEndo);
		listTipiFamigliaEndo.add(tipifamiglieendo);
		contatore = 0;
	    }
	}
	return listTipiFamigliaEndo;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.isRecordPresentiMap = new HashMap<String, Boolean>();
    }

    private Map<String, Boolean> isRecordPresentiMap = new HashMap<String, Boolean>();

    private String getMapKey(String idcomunealias, String software) {

	return idcomunealias + "-" + software;
    }

    @Override
    public boolean existsRecords() {

	if (isRecordPresentiMap == null) {
	    isRecordPresentiMap = new HashMap<String, Boolean>();
	}
	String key = getMapKey(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware());
	if (isRecordPresentiMap.get(key) != null) {
	    return isRecordPresentiMap.get(key);
	} else {
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction softwareAttivoAndTT = new FilterRestriction();
	    softwareAttivoAndTT.addFilterField(FilterUtils.in("software.codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() },
		    String.class));
	    filterTable.addRestriction(softwareAttivoAndTT);
	    boolean exists = tipifamiglieendoDAO.existsRecords(filterTable);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }

    @Override
    public List<Tipifamiglieendo> findTipifamigliaByDescrizione(String testoDaCercare, String tipoRicerca, String campiRicerca,
	    FlagPubblicaEnum pubblicaEnum, Integer firstResult, Integer maxResult) {

	if (StringUtils.isBlank(testoDaCercare)) {
	    return new ArrayList<Tipifamiglieendo>();
	}
	testoDaCercare = testoDaCercare.replaceAll("%", "");
	if (StringUtils.isBlank(testoDaCercare)) {
	    return new ArrayList<Tipifamiglieendo>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction software = new FilterRestriction();
	software.addFilterField(FilterUtils.in("software.codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, String.class));
	software.addFilterField(FilterUtils.isNotEmpty("inventarioprocedimentis", "tipiendos"));
	ft.addRestriction(software);
	// Controlla se deve filtrare per il flag_pubblica
	FilterRestriction flagPubblicaFr = new FilterRestriction();
	switch (pubblicaEnum) {
	case DA_PUBBLICARE:
	    flagPubblicaFr.addFilterField(FilterUtils.equals("flagPubblica", Boolean.TRUE, Boolean.class));
	    ft.addRestriction(flagPubblicaFr);
	    break;
	case NON_PUBBLICARE:
	    flagPubblicaFr.addFilterField(FilterUtils.equals("flagPubblica", Boolean.FALSE, Boolean.class));
	    ft.addRestriction(flagPubblicaFr);
	    break;
	default:
	    break;
	}
	tipoRicerca = StringUtils.defaultIfEmpty(tipoRicerca, "tutteParole");
	campiRicerca = StringUtils.defaultIfEmpty(campiRicerca, "titoli");
	if (tipoRicerca.equals("tutteParole")) {
	    String[] valori = testoDaCercare.split(" ");
	    for (String v : valori) {
		FilterRestriction fr = new FilterRestriction();
		fr.addFilterField(FilterUtils.like("tipo", v));
		ft.addRestriction(fr);
	    }
	} else if (tipoRicerca.equals("interaFrase")) {
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.like("tipo", testoDaCercare));
	    ft.addRestriction(fr);
	} else { // almenoUnaParola
	    String[] valori = testoDaCercare.split(" ");
	    FilterRestriction fr = new FilterRestriction();
	    fr.setAndOrRestriction(AndOrRestriction.OR);
	    for (String v : valori) {
		fr.addFilterField(FilterUtils.like("tipo", v));
	    }
	    ft.addRestriction(fr);
	}
	List<Tipifamiglieendo> res = tipifamiglieendoDAO.findByFilterTable(ft, firstResult, maxResult);
	return res;
    }

    private void dataIntegration(Tipifamiglieendo entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro Tipifamiglieendo è nullo");
	}
	if (entity.getFlagPubblica() == null) {
	    entity.setFlagPubblica(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }
}
