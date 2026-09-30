package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.CatastoDAO;
import it.gruppoinit.pal.gp.core.domain.Catasto;
import it.gruppoinit.pal.gp.core.service.CatastoService;

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
public class CatastoServiceImpl extends BaseServiceImpl<Catasto, String> implements CatastoService {

    private static final String LISTA_CATASTO = "LISTA_CATASTO";
    private CatastoDAO catastoDAO;
    private Map<String, List<Catasto>> cachedList;

    @Autowired
    public void setCatastoDAO(CatastoDAO catastoDAO) {

	this.catastoDAO = catastoDAO;
    }

    private String getMapKey() {

	return LISTA_CATASTO;
    }

    @Override
    protected Class<Catasto> getEntityClass() {

	return Catasto.class;
    }

    @Override
    public List<Catasto> findAll() {

	if (cachedList == null) {
	    cachedList = new HashMap<String, List<Catasto>>();
	}
	String key = getMapKey();
	if (cachedList.isEmpty()) {
	    List<Catasto> newList = new ArrayList<Catasto>();
	    List<Catasto> list = catastoDAO.findAll(null, null);
	    int i = 0;
	    for (Catasto c : list) {
		Catasto sc = new Catasto();
		sc.setCodice(c.getCodice());
		sc.setDescrizione(c.getDescrizione());
		newList.add(i, sc);
		i++;
	    }
	    cachedList.put(key, newList);
	    return newList;
	} else {
	    return cachedList.get(key);
	}
    }

    @Override
    public List<Catasto> findAll(Integer firstResult, Integer maxResult) {

	return catastoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Catasto entity) {

	if (validateEntity(entity)) {
	    catastoDAO.insert(entity);
	    resetObjectCached();
	}
    }

    @Override
    public Catasto findById(String id) {

	return catastoDAO.findById(id);
    }

    @Override
    public void update(Catasto entity) {

	if (validateEntity(entity)) {
	    catastoDAO.update(entity);
	    resetObjectCached();
	}
    }

    @Override
    public void delete(Catasto entity) {

	if (isDeleteAllowed(entity)) {
	    catastoDAO.delete(entity);
	    resetObjectCached();
	}
    }

    protected boolean isDeleteAllowed(Catasto entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.cachedList = new HashMap<String, List<Catasto>>();
    }
}
