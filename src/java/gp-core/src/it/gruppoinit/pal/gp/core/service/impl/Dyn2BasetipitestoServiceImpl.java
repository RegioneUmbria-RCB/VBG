package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2BasetipitestoDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basetipitesto;
import it.gruppoinit.pal.gp.core.service.Dyn2BasetipitestoService;

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
public class Dyn2BasetipitestoServiceImpl extends BaseServiceImpl<Dyn2Basetipitesto, String> implements Dyn2BasetipitestoService {

    private Dyn2BasetipitestoDAO dyn2basetipitestoDAO;

    @Autowired
    public void setDyn2BasetipitestoDAO(Dyn2BasetipitestoDAO dyn2basetipitestoDAO) {

	this.dyn2basetipitestoDAO = dyn2basetipitestoDAO;
    }

    @Override
    protected Class<Dyn2Basetipitesto> getEntityClass() {

	return Dyn2Basetipitesto.class;
    }

    @Override
    public List<Dyn2Basetipitesto> findAll(Integer firstResult, Integer maxResult) {

	return dyn2basetipitestoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Dyn2Basetipitesto entity) {

	if (validateEntity(entity)) {
	    dyn2basetipitestoDAO.insert(entity);
	}
    }

    @Override
    public Dyn2Basetipitesto findById(String id) {

	return dyn2basetipitestoDAO.findById(id);
    }

    @Override
    public void update(Dyn2Basetipitesto entity) {

	if (validateEntity(entity)) {
	    dyn2basetipitestoDAO.update(entity);
	}
    }

    @Override
    public void delete(Dyn2Basetipitesto entity) {

	if (isDeleteAllowed(entity)) {
	    dyn2basetipitestoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Dyn2Basetipitesto entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO_validare_la_delete
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
