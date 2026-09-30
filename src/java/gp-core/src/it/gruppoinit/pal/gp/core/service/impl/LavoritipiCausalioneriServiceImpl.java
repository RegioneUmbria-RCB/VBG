package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.LavoritipiCausalioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.LavoritipiCausalioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.LavoritipiCausalioneriService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class LavoritipiCausalioneriServiceImpl extends BaseServiceImpl<LavoritipiCausalioneri, PkId> implements LavoritipiCausalioneriService {

    private LavoritipiCausalioneriDAO lavoritipicausalioneriDAO;

    @Autowired
    public void setLavoritipiCausalioneriDAO(LavoritipiCausalioneriDAO lavoritipicausalioneriDAO) {

	this.lavoritipicausalioneriDAO = lavoritipicausalioneriDAO;
    }

    @Override
    protected Class<LavoritipiCausalioneri> getEntityClass() {

	return LavoritipiCausalioneri.class;
    }

    @Override
    public List<LavoritipiCausalioneri> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return lavoritipicausalioneriDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(LavoritipiCausalioneri entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    lavoritipicausalioneriDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public LavoritipiCausalioneri findById(PkId id) {

	// §§§BEGIN§§§
	return lavoritipicausalioneriDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(LavoritipiCausalioneri entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    lavoritipicausalioneriDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(LavoritipiCausalioneri entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    lavoritipicausalioneriDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(LavoritipiCausalioneri entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREING_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public List<LavoritipiCausalioneri> findByLavoritipi(Lavoritipi lavoritipi) {

	// §§§BEGIN§§§
	return lavoritipicausalioneriDAO.findByLavoritipi(lavoritipi);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private Map<String, Boolean> isRecordPresentiMap = new HashMap<String, Boolean>();

    private String getMapKey(String idcomunealias, String software) {

	return idcomunealias + "-" + software;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	// §§§BEGIN§§§
	this.isRecordPresentiMap = new HashMap<String, Boolean>();
	// §§§END§§§
    }

    @Override
    public boolean existsRecords() {

	// §§§BEGIN§§§
	if (isRecordPresentiMap == null) {
	    isRecordPresentiMap = new HashMap<String, Boolean>();
	}
	String key = getMapKey(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware());
	if (isRecordPresentiMap.get(key) != null) {
	    return isRecordPresentiMap.get(key);
	} else {
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	    boolean exists = lavoritipicausalioneriDAO.existsRecords(filterTable);
	    isRecordPresentiMap.put(key, Boolean.valueOf(exists));
	    return exists;
	}
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return false;@@@ENDALTERNATIVEEXIT@@@
    }
}
