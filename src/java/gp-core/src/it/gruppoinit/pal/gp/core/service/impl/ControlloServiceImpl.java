package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ControlloDAO;
import it.gruppoinit.pal.gp.core.domain.Controllo;
import it.gruppoinit.pal.gp.core.domain.ControlloId;
import it.gruppoinit.pal.gp.core.service.ControlloService;

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
public class ControlloServiceImpl extends BaseServiceImpl<Controllo, ControlloId> implements ControlloService {

    private ControlloDAO controlloDAO;

    @Autowired
    public void setControlloDAO(ControlloDAO controlloDAO) {

	this.controlloDAO = controlloDAO;
    }

    @Override
    protected Class<Controllo> getEntityClass() {

	return Controllo.class;
    }

    @Override
    public List<Controllo> findAll(Integer firstResult, Integer maxResult) {

	return controlloDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Controllo entity) {

	if (validateEntity(entity)) {
	    controlloDAO.insert(entity);
	}
    }

    @Override
    public Controllo findById(ControlloId id) {

	return controlloDAO.findById(id);
    }

    @Override
    public void update(Controllo entity) {

	if (validateEntity(entity)) {
	    controlloDAO.update(entity);
	}
    }

    @Override
    public void delete(Controllo entity) {

	if (isDeleteAllowed(entity)) {
	    controlloDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Controllo entity) {

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
