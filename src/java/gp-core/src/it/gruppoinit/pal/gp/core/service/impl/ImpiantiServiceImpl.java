package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ImpiantiDAO;
import it.gruppoinit.pal.gp.core.dao.ImpiantiprocedureDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Impianti;
import it.gruppoinit.pal.gp.core.domain.Impiantiprocedure;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ImpiantiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class ImpiantiServiceImpl extends BaseServiceImpl<Impianti, PkId> implements ImpiantiService {

    private ImpiantiDAO impiantiDAO;
    private ImpiantiprocedureDAO impiantiprocedureDAO;
    private IstanzeService istanzeService;

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setImpiantiDAO(ImpiantiDAO impiantiDAO) {

	this.impiantiDAO = impiantiDAO;
    }

    @Autowired
    public void setImpiantiprocedureDAO(ImpiantiprocedureDAO impiantiprocedureDAO) {

	this.impiantiprocedureDAO = impiantiprocedureDAO;
    }

    @Override
    protected Class<Impianti> getEntityClass() {

	return Impianti.class;
    }

    @Override
    public List<Impianti> findAll(Integer firstResult, Integer maxResult) {

	return impiantiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Impianti entity) {

	if (validateEntity(entity)) {
	    impiantiDAO.insert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public Impianti findById(PkId id) {

	return impiantiDAO.findById(id);
    }

    @Override
    public void update(Impianti entity) {

	if (validateEntity(entity)) {
	    impiantiDAO.update(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void delete(Impianti entity) {

	if (isDeleteAllowed(entity)) {
	    Set<Impiantiprocedure> procedures = entity.getImpiantiprocedures();
	    for (Impiantiprocedure impiantiprocedure : procedures) {
		impiantiprocedureDAO.delete(impiantiprocedure);
	    }
	    impiantiDAO.delete(entity);
	    resetObjectCached();
	}
    }

    protected boolean isDeleteAllowed(Impianti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<Istanze> istanzes = istanzeService.findByImpianti(entity.getId().getCodice(), 0, 2);
	if (!istanzes.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private Map<String, Boolean> isRecordPresentiMap = new HashMap<String, Boolean>();

    private String getMapKey(String idcomunealias, String software) {

	return idcomunealias + "-" + software;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.isRecordPresentiMap = new HashMap<String, Boolean>();
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
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	    boolean exists = impiantiDAO.existsRecords(filterTable);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }

    @Override
    public List<Impianti> findByDescrizione(String textToSearch) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (StringUtils.isNotBlank(textToSearch)) {
	    if (!textToSearch.equalsIgnoreCase("%")) {
		FilterRestriction descrizione = new FilterRestriction();
		descrizione.addFilterField(FilterUtils.like("impianto", textToSearch));
		ft.addRestriction(descrizione);
	    }
	}
	ft.addOrder(FilterUtils.orderAsc("impianto"));
	return impiantiDAO.findByFilterTable(ft);
    }
}
