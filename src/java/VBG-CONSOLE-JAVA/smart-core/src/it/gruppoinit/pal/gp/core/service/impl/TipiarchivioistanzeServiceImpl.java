package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.TipiarchivioistanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.TipiarchivioistanzeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipiarchivioistanzeServiceImpl extends BaseServiceImpl<Tipiarchivioistanze, PkId> implements TipiarchivioistanzeService {

    private TipiarchivioistanzeDAO tipiarchivioistanzeDAO;

    @Autowired
    public void setTipiarchivioistanzeDAO(TipiarchivioistanzeDAO tipiarchivioistanzeDAO) {

	this.tipiarchivioistanzeDAO = tipiarchivioistanzeDAO;
    }

    @Override
    protected Class<Tipiarchivioistanze> getEntityClass() {

	return Tipiarchivioistanze.class;
    }

    @Override
    public void delete(Tipiarchivioistanze entity) {

	if (isDeleteAllowed(entity)) {
	    tipiarchivioistanzeDAO.delete(entity);
	    resetObjectCached();
	}
    }

    @Override
    public List<Tipiarchivioistanze> findAll(Integer firstResult, Integer maxResult) {

	return tipiarchivioistanzeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Tipiarchivioistanze findById(PkId id) {

	return tipiarchivioistanzeDAO.findById(id);
    }

    @Override
    public void insert(Tipiarchivioistanze entity) {

	if (validateEntity(entity)) {
	    tipiarchivioistanzeDAO.insert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void update(Tipiarchivioistanze entity) {

	if (validateEntity(entity)) {
	    tipiarchivioistanzeDAO.update(entity);
	    resetObjectCached();
	}
    }

    protected boolean isDeleteAllowed(Tipiarchivioistanze entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	return delete;
    }

    @Override
    public List<Tipiarchivioistanze> findByFilterTable(FilterTable filterTable) {

	return tipiarchivioistanzeDAO.findByFilterTable(filterTable);
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
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	    boolean exists = tipiarchivioistanzeDAO.existsRecords(filterTable);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }
}
