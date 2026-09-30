package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModellidtestiDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellidtesti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidtestiService;

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
public class Dyn2ModellidtestiServiceImpl extends BaseServiceImpl<Dyn2Modellidtesti, PkId> implements Dyn2ModellidtestiService {

    private Dyn2ModellidtestiDAO dyn2modellidtestiDAO;

    @Autowired
    public void setDyn2ModellidtestiDAO(Dyn2ModellidtestiDAO dyn2modellidtestiDAO) {

	this.dyn2modellidtestiDAO = dyn2modellidtestiDAO;
    }

    @Override
    protected Class<Dyn2Modellidtesti> getEntityClass() {

	return Dyn2Modellidtesti.class;
    }

    @Override
    public List<Dyn2Modellidtesti> findAll(Integer firstResult, Integer maxResult) {

	return dyn2modellidtestiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Dyn2Modellidtesti entity) {

	if (validateEntity(entity)) {
	    dyn2modellidtestiDAO.insert(entity);
	}
    }

    @Override
    public Dyn2Modellidtesti findById(PkId id) {

	return dyn2modellidtestiDAO.findById(id);
    }

    @Override
    public void update(Dyn2Modellidtesti entity) {

	if (validateEntity(entity)) {
	    dyn2modellidtestiDAO.update(entity);
	}
    }

    @Override
    public void delete(Dyn2Modellidtesti entity) {

	if (isDeleteAllowed(entity)) {
	    dyn2modellidtestiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Dyn2Modellidtesti entity) {

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
