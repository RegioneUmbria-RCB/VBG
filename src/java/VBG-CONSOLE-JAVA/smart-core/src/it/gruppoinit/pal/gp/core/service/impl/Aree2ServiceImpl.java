package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.Aree2DAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Aree2;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Aree2Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Riccardo Bocci
 */
@Service
public class Aree2ServiceImpl extends BaseServiceImpl<Aree2, PkId> implements Aree2Service {

    private Aree2DAO aree2DAO;

    @Autowired
    public void setAree2DAO(Aree2DAO aree2DAO) {

	this.aree2DAO = aree2DAO;
    }

    @Override
    protected Class<Aree2> getEntityClass() {

	return Aree2.class;
    }

    @Override
    public List<Aree2> findAll(Integer firstResult, Integer maxResult) {

	return aree2DAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Aree2 entity) {

	if (validateEntity(entity)) {
	    aree2DAO.insert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public Aree2 findById(PkId id) {

	return aree2DAO.findById(id);
    }

    @Override
    public void update(Aree2 entity) {

	if (validateEntity(entity)) {
	    aree2DAO.update(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void delete(Aree2 entity) {

	if (isDeleteAllowed(entity)) {
	    aree2DAO.delete(entity);
	    resetObjectCached();
	}
    }

    protected boolean isDeleteAllowed(Aree2 entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
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
	    boolean exists = aree2DAO.existsRecords(filterTable);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }

    @Override
    public List<Aree2> findByDescrizione(String descrizione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (StringUtils.isNotBlank(descrizione) || !descrizione.equals("%")) {
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.like("denominazione", descrizione));
	    ft.addRestriction(fr);
	}
	ft.addOrder(FilterUtils.orderAsc("denominazione"));
	return aree2DAO.findByFilterTable(ft);
    }
}
