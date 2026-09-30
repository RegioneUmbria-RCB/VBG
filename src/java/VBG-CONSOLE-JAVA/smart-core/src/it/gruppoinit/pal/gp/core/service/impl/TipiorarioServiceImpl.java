package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.TipiorarioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;
import it.gruppoinit.pal.gp.core.domain.Tipiorariodettaglio;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.TipiorarioService;
import it.gruppoinit.pal.gp.core.service.TipiorariodettaglioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author lucap
 */
@Service
public class TipiorarioServiceImpl extends BaseServiceImpl<Tipiorario, PkId> implements TipiorarioService {

    private TipiorarioDAO tipiorarioDAO;
    private TipiorariodettaglioService tipiorariodettaglioService;

    @Autowired
    public void setTipiorarioDAO(TipiorarioDAO tipiorarioDAO) {

	this.tipiorarioDAO = tipiorarioDAO;
    }

    @Autowired
    public void setTipiorariodettaglioService(TipiorariodettaglioService tipiorariodettaglioService) {

	this.tipiorariodettaglioService = tipiorariodettaglioService;
    }

    @Override
    protected Class<Tipiorario> getEntityClass() {

	return Tipiorario.class;
    }

    @Override
    public List<Tipiorario> findAll(Integer firstResult, Integer maxResult) {

	return tipiorarioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipiorario entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<Tipiorariodettaglio> tipiorariodettaglioList = entity.getTipiorariodettaglios();
	    entity.setTipiorariodettaglios(null);
	    tipiorarioDAO.insert(entity);
	    childDataInsert(entity, tipiorariodettaglioList, true);
	    resetObjectCached();
	}
    }

    @Override
    public Tipiorario findById(PkId id) {

	return tipiorarioDAO.findById(id);
    }

    @Override
    public void update(Tipiorario entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<Tipiorariodettaglio> tipiorariodettaglioList = entity.getTipiorariodettaglios();
	    tipiorariodettaglioService.deleteByTipiorario(entity);
	    entity.setTipiorariodettaglios(null);
	    tipiorarioDAO.update(entity);
	    childDataInsert(entity, tipiorariodettaglioList, false);
	    resetObjectCached();
	}
    }

    @Override
    public void delete(Tipiorario entity) {

	if (isDeleteAllowed(entity)) {
	    tipiorariodettaglioService.deleteByTipiorario(entity);
	    tipiorarioDAO.delete(entity);
	    resetObjectCached();
	}
    }

    protected boolean isDeleteAllowed(Tipiorario entity) {

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
	    boolean exists = tipiorarioDAO.existsRecords(filterTable);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }

    @Override
    public List<Tipiorario> findByFilter(Tipiorario entity) {

	return tipiorarioDAO.findByFilter(entity);
    }

    private void childDataInsert(Tipiorario entity, Set<Tipiorariodettaglio> tipiorariodettaglioList, Boolean isInsert) {

	for (Tipiorariodettaglio tipiorariodettaglio : tipiorariodettaglioList) {
	    tipiorariodettaglioService.insert(tipiorariodettaglio);
	}
    }

    private void dataIntegration(Tipiorario entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro tipiorario è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Tipiorario entity) {

    }
}
