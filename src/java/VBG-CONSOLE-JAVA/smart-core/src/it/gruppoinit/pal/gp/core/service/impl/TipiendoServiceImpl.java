package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipiendoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipiendoServiceImpl extends BaseServiceImpl<Tipiendo, PkId> implements TipiendoService {

    private TipiendoDAO tipiendoDAO;

    @Autowired
    public void setTipiendoDAO(TipiendoDAO tipiendoDAO) {

	this.tipiendoDAO = tipiendoDAO;
    }

    private InventarioprocedimentiService inventarioprocedimentiService;

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Override
    protected Class<Tipiendo> getEntityClass() {

	return Tipiendo.class;
    }

    @Override
    public void delete(Tipiendo entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	InvalidValue iv = null;
	int countEndoByTipo = inventarioprocedimentiService.countByTipiendo(entity);
	if (countEndoByTipo > 0) {
	    iv = new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "INVENTARIOPROCEDIMENTI", null);
	    _ivs.add(iv);
	    this.throwValidationMessages(_ivs);
	}
	tipiendoDAO.delete(entity);
    }

    @Override
    public List<Tipiendo> findAll(Integer firstResult, Integer maxResult) {

	return tipiendoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Tipiendo findById(PkId id) {

	return tipiendoDAO.findById(id);
    }

    @Override
    public void insert(Tipiendo entity) {

	if (validateEntity(entity)) {
	    tipiendoDAO.insert(entity);
	}
    }

    @Override
    public void update(Tipiendo entity) {

	if (validateEntity(entity)) {
	    tipiendoDAO.update(entity);
	}
    }

    @Override
    public List<Tipiendo> findByDescSWeTT(String textToSearch, Integer codiceFamiglia) {

	return tipiendoDAO.findByDescSWeTT(textToSearch, codiceFamiglia);
    }

    @Override
    public List<Tipiendo> findByTipiFamiglieEndo(Tipifamiglieendo tipifamiglieendo) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction basefilter = new FilterRestriction();
	basefilter.addFilterField(FilterUtils.equals("id.codice", tipifamiglieendo.getId().getCodice(), "tipifamiglieendo", Integer.class));
	basefilter.addFilterField(FilterUtils.in("codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, "software",
		String.class));
	filterTable.addRestriction(basefilter);
	filterTable.addOrder(FilterUtils.order("ordine", OrderTypeEnum.ASC));
	filterTable.addOrder(FilterUtils.order("tipo", OrderTypeEnum.ASC));
	return tipiendoDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Tipiendo> findByDescAndSW(String textToSearch, Integer codiceFamiglia, String[] codicesoftware, String idcomunebase) {

	FilterTable ft = null;
	FilterRestriction filterRestriction = new FilterRestriction();
	if (StringUtils.isNotBlank(idcomunebase)) {
	    ft = new FilterTable(DAOEnum.FIND_ALL);
	    filterRestriction.addFilterField(FilterUtils.equals("id.idcomune", idcomunebase, String.class));
	} else {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	}
	// Filtro per famiglia se è passato il codice
	if (codiceFamiglia != null) {
	    filterRestriction.addFilterField(FilterUtils.equals("id.codice", codiceFamiglia, "tipifamiglieendo", Integer.class));
	}
	// Filtro per codice o per descrizione
	if (StringUtils.isNotBlank(textToSearch)) {
	    try {
		filterRestriction.addFilterField(FilterUtils.equals("id.codice", Integer.parseInt(textToSearch.replaceAll("%", "")), Integer.class));
	    } catch (Exception e) {
		filterRestriction.addFilterField(FilterUtils.like("tipo", textToSearch));
	    }
	}
	// Filtro per software,il filtro software è sempre impostato. E quello rpesente sull' OMRHELPER
	// se non viene passato, altrimenti viene impostato quello passato alla signatura del metodo
	if (codicesoftware != null) {
	    filterRestriction.addFilterField(FilterUtils.in("codice", codicesoftware, "software", String.class));
	} else {
	    filterRestriction.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	}
	ft.addRestriction(filterRestriction);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("tipo"));
	return tipiendoDAO.findByFilterTable(ft);
    }

    @Override
    public int countByTipiFamiglieEndo(Tipifamiglieendo tipifamiglieendo) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction basefilter = new FilterRestriction();
	basefilter.addFilterField(FilterUtils.equals("tipifamiglieendoId", tipifamiglieendo.getId().getCodice(), Integer.class));
	filterTable.addRestriction(basefilter);
	return tipiendoDAO.countRecord(filterTable);
    }

    @Override
    public List<Tipiendo> findByTipiendo(Tipiendo tipiendo, Integer firstResult, Integer maxResult) {

	FilterTable ft = getTipiendoToFilterTable(tipiendo);
	return tipiendoDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    public FilterTable getTipiendoToFilterTable(Tipiendo tipiendo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
	filterRestriction.addFilterField(FilterUtils.equals("id.idcomune", tipiendo.getId().getIdcomune(), String.class));
	if (EntityUtils.getNestedProperty(tipiendo.getTipifamiglieendo(), "id.codice") != null) {
	    filterRestriction.addFilterField(FilterUtils.equals("id.codice", tipiendo.getTipifamiglieendo().getId().getCodice(), "tipifamiglieendo",
		    Integer.class));
	}
	ft.addRestriction(filterRestriction);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("tipo"));
	return ft;
    }

    @Override
    public List<Tipiendo> findTipiendoByDescrizione(String testoDaCercare, String tipoRicerca, String campiRicerca, Integer firstResult,
	    Integer maxResult) {

	if (StringUtils.isBlank(testoDaCercare)) {
	    return new ArrayList<Tipiendo>();
	}
	testoDaCercare = testoDaCercare.replaceAll("%", "");
	if (StringUtils.isBlank(testoDaCercare)) {
	    return new ArrayList<Tipiendo>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction software = new FilterRestriction();
	software.addFilterField(FilterUtils.in("software.codice", new String[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }, String.class));
	software.addFilterField(FilterUtils.isNotEmpty("inventarioprocedimentis"));
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
	List<Tipiendo> res = tipiendoDAO.findByFilterTable(ft, firstResult, maxResult);
	return res;
    }
}
