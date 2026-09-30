package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.StradariocoloreDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.StradariocoloreId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.StradariocoloreService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class StradariocoloreServiceImpl extends BaseServiceImpl<Stradariocolore, StradariocoloreId> implements StradariocoloreService {

    private StradariocoloreDAO stradariocoloreDAO;
    private Map<String, List<Stradariocolore>> cachedList;

    @Autowired
    public void setStradariocoloreDAO(StradariocoloreDAO stradariocoloreDAO) {

	this.stradariocoloreDAO = stradariocoloreDAO;
    }

    @Override
    protected Class<Stradariocolore> getEntityClass() {

	return Stradariocolore.class;
    }

    @Override
    public List<Stradariocolore> findAll(Integer firstResult, Integer maxResult) {

	return stradariocoloreDAO.findAll(firstResult, maxResult);
    }

    @Override
    public List<Stradariocolore> findAll() {

	if (cachedList == null) {
	    cachedList = new HashMap<String, List<Stradariocolore>>();
	}
	String key = getMapKey(ORMHelper.getIdcomuneAlias());
	List<Stradariocolore> ret = cachedList.get(key);
	if (ret == null) {
	    ret = new ArrayList<Stradariocolore>();
	    List<Stradariocolore> list = stradariocoloreDAO.findAll(null, null);
	    int i = 0;
	    for (Stradariocolore stradariocolore : list) {
		StradariocoloreId id = new StradariocoloreId(stradariocolore.getId().getCodicecolore());
		Stradariocolore sc = new Stradariocolore();
		sc.setId(id);
		sc.setColore(stradariocolore.getColore());
		ret.add(i, sc);
		i++;
	    }
	    cachedList.put(key, ret);
	}
	return ret;
    }

    @Override
    public void insert(Stradariocolore entity) {

	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    // forzo l'inserimento del codice maiuscolo
	    entity.getId().setCodicecolore(entity.getId().getCodicecolore().toUpperCase());
	    stradariocoloreDAO.insert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public Stradariocolore findById(StradariocoloreId id) {

	return stradariocoloreDAO.findById(id);
    }

    @Override
    public void update(Stradariocolore entity) {

	if (validateEntity(entity)) {
	    stradariocoloreDAO.update(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void delete(Stradariocolore entity) {

	if (isDeleteAllowed(entity)) {
	    stradariocoloreDAO.delete(entity);
	    resetObjectCached();
	}
    }

    protected boolean isDeleteAllowed(Stradariocolore entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getIstanzestradarios().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZESTRADARIO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    protected boolean isInsertAllowed(Stradariocolore entity) {

	boolean insert = true;
	// forzo il codice a maiuscolo la ricerca deve essere caseInsenitive
	entity.getId().setCodicecolore(entity.getId().getCodicecolore().toUpperCase());
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Stradariocolore objectDB = null;
	objectDB = stradariocoloreDAO.findById(entity.getId());
	if (objectDB != null && !objectDB.getId().getCodicecolore().equals("")) {
	    _ivs.add(new InvalidValue("service_error.duplicate_codice", null, null, entity.getId().getCodicecolore(), null));
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.isRecordPresentiMap = new HashMap<String, Boolean>();
	this.cachedList = new HashMap<String, List<Stradariocolore>>();
    }

    private Map<String, Boolean> isRecordPresentiMap = new HashMap<String, Boolean>();

    private String getMapKey(String idcomunealias) {

	return idcomunealias;
    }

    @Override
    public boolean existsRecords() {

	if (isRecordPresentiMap == null) {
	    isRecordPresentiMap = new HashMap<String, Boolean>();
	}
	String key = getMapKey(ORMHelper.getIdcomuneAlias());
	if (isRecordPresentiMap.get(key) != null) {
	    return isRecordPresentiMap.get(key);
	} else {
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    boolean exists = stradariocoloreDAO.existsRecords(filterTable);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
    }
}
