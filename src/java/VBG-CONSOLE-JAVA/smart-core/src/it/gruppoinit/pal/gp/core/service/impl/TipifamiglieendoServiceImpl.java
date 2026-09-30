package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipifamiglieendoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	    tipifamiglieendoDAO.insert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void update(Tipifamiglieendo entity) {

	if (validateEntity(entity)) {
	    tipifamiglieendoDAO.update(entity);
	    resetObjectCached();
	}
    }

    @Override
    protected Class<Tipifamiglieendo> getEntityClass() {

	return Tipifamiglieendo.class;
    }

    @Override
    public List<Tipifamiglieendo> findByFilter(Tipifamiglieendo entity, String idcomune) {

	return tipifamiglieendoDAO.findByFilter(entity, idcomune);
    }

    @Override
    public List<Tipifamiglieendo> findByDescSWeTT(String textToSearch) {

	return tipifamiglieendoDAO.findByDescSWeTT(textToSearch);
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
    public List<Tipifamiglieendo> findTipifamigliaByDescrizione(String testoDaCercare, String tipoRicerca, String campiRicerca, Integer firstResult,
	    Integer maxResult) {

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

    @Override
    public List<Tipifamiglieendo> findAllBase(Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomunebase(), String.class));
	fr.addFilterField(FilterUtils.in("software.codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("tipo"));
	return tipifamiglieendoDAO.findByFilterTable(ft, firstResult, maxResults);
    }

    @Override
    public List<Tipifamiglieendo> findByDescAndSW(String testoDaCercare, String[] software, String idcomune) {

	if (StringUtils.isBlank(testoDaCercare)) {
	    return new ArrayList<Tipifamiglieendo>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction softwaref = new FilterRestriction();
	softwaref.addFilterField(FilterUtils.in("software.codice", software, String.class));
	softwaref.addFilterField(FilterUtils.isNotEmpty("inventarioprocedimentis", "tipiendos"));
	ft.addRestriction(softwaref);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.like("tipo", "%" + testoDaCercare + "%"));
	ft.addRestriction(fr);
	List<Tipifamiglieendo> res = tipifamiglieendoDAO.findByFilterTable(ft);
	return res;
    }
}
