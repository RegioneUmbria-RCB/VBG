package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2BasecontestiDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basecontesti;
import it.gruppoinit.pal.gp.core.service.Dyn2BasecontestiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class Dyn2BasecontestiServiceImpl extends BaseServiceImpl<Dyn2Basecontesti, String> implements Dyn2BasecontestiService {

    private Dyn2BasecontestiDAO dyn2basecontestiDAO;

    @Autowired
    public void setDyn2BasecontestiDAO(Dyn2BasecontestiDAO dyn2basecontestiDAO) {

	this.dyn2basecontestiDAO = dyn2basecontestiDAO;
    }

    @Override
    protected Class<Dyn2Basecontesti> getEntityClass() {

	return Dyn2Basecontesti.class;
    }

    @Override
    public List<Dyn2Basecontesti> findAll(Integer firstResult, Integer maxResult) {

	return dyn2basecontestiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Dyn2Basecontesti entity) {

	if (validateEntity(entity)) {
	    dyn2basecontestiDAO.insert(entity);
	}
    }

    @Override
    public Dyn2Basecontesti findById(String id) {

	return dyn2basecontestiDAO.findById(id);
    }

    @Override
    public void update(Dyn2Basecontesti entity) {

	if (validateEntity(entity)) {
	    dyn2basecontestiDAO.update(entity);
	}
    }

    @Override
    public void delete(Dyn2Basecontesti entity) {

	if (isDeleteAllowed(entity)) {
	    dyn2basecontestiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Dyn2Basecontesti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
