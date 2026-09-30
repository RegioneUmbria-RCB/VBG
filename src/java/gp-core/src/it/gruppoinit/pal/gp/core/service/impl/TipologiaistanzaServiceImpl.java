/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipologiaistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.TipologiaistanzaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class TipologiaistanzaServiceImpl extends BaseServiceImpl<Tipologiaistanza, PkId> implements TipologiaistanzaService {

    private TipologiaistanzaDAO tipologiaistanzaDAO;
    private IstanzeService istanzeService;

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setTipologiaistanzaDAO(TipologiaistanzaDAO tipologiaistanzaDAO) {

	this.tipologiaistanzaDAO = tipologiaistanzaDAO;
    }

    @Override
    protected Class<Tipologiaistanza> getEntityClass() {

	return Tipologiaistanza.class;
    }

    @Override
    public void delete(Tipologiaistanza entity) {

	if (isDeleteAllowed(entity)) {
	    tipologiaistanzaDAO.delete(entity);
	    resetObjectCached();
	}
    }

    protected boolean isDeleteAllowed(Tipologiaistanza entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<Istanze> istanzes = istanzeService.findByTipologiaIstanza(entity.getId().getCodice(), 0, 2);
	if (!istanzes.isEmpty()) {
	    delete = false;
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ISTANZE", null));
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Tipologiaistanza> findAll(Integer firstResult, Integer maxResult) {

	return tipologiaistanzaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Tipologiaistanza findById(PkId id) {

	return tipologiaistanzaDAO.findById(id);
    }

    @Override
    public void insert(Tipologiaistanza entity) {

	if (validateEntity(entity)) {
	    tipologiaistanzaDAO.insert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void update(Tipologiaistanza entity) {

	if (validateEntity(entity)) {
	    tipologiaistanzaDAO.update(entity);
	    resetObjectCached();
	}
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
	    boolean exists = tipologiaistanzaDAO.existsRecords(filterTable);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }

    @Override
    public List<Tipologiaistanza> findByFilterTable(FilterTable filterTable) {

	return tipologiaistanzaDAO.findByFilterTable(filterTable);
    }

    @Override
    protected Tipologiaistanza customBindDomainObject(Tipologiaistanza entity) {

	if (entity == null) {
	    return null;
	}
	if (StringUtils.isNotBlank(entity.getTiDescrizione())) {
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	    FilterRestriction filterRestriction = new FilterRestriction();
	    filterRestriction.addFilterField(FilterUtils.equalsIgnoreCase("tiDescrizione", entity.getTiDescrizione().trim()));
	    filterTable.addRestriction(filterRestriction);
	    List<Tipologiaistanza> list = tipologiaistanzaDAO.findByFilterTable(filterTable);
	    if (list.size() >= 1) {
		return list.get(0);
	    }
	}
	return null;
    }
}
