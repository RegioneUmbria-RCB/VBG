package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ControlloverificheDAO;
import it.gruppoinit.pal.gp.core.domain.Controlloverifiche;
import it.gruppoinit.pal.gp.core.domain.ControlloverificheId;
import it.gruppoinit.pal.gp.core.service.ControlloverificheService;

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
public class ControlloverificheServiceImpl extends BaseServiceImpl<Controlloverifiche, ControlloverificheId> implements ControlloverificheService {

    private ControlloverificheDAO controlloverificheDAO;

    @Autowired
    public void setControlloverificheDAO(ControlloverificheDAO controlloverificheDAO) {

	this.controlloverificheDAO = controlloverificheDAO;
    }

    @Override
    protected Class<Controlloverifiche> getEntityClass() {

	return Controlloverifiche.class;
    }

    @Override
    public List<Controlloverifiche> findAll(Integer firstResult, Integer maxResult) {

	return controlloverificheDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Controlloverifiche entity) {

	if (validateEntity(entity)) {
	    controlloverificheDAO.insert(entity);
	}
    }

    @Override
    public Controlloverifiche findById(ControlloverificheId id) {

	return controlloverificheDAO.findById(id);
    }

    @Override
    public void update(Controlloverifiche entity) {

	if (validateEntity(entity)) {
	    controlloverificheDAO.update(entity);
	}
    }

    @Override
    public void delete(Controlloverifiche entity) {

	if (isDeleteAllowed(entity)) {
	    controlloverificheDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Controlloverifiche entity) {

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
