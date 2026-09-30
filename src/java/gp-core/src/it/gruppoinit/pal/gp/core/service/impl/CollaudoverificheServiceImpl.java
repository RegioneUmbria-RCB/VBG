package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CollaudoverificheDAO;
import it.gruppoinit.pal.gp.core.domain.Collaudoverifiche;
import it.gruppoinit.pal.gp.core.domain.CollaudoverificheId;
import it.gruppoinit.pal.gp.core.service.CollaudoverificheService;

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
public class CollaudoverificheServiceImpl extends BaseServiceImpl<Collaudoverifiche, CollaudoverificheId> implements CollaudoverificheService {

    private CollaudoverificheDAO collaudoverificheDAO;

    @Autowired
    public void setCollaudoverificheDAO(CollaudoverificheDAO collaudoverificheDAO) {

	this.collaudoverificheDAO = collaudoverificheDAO;
    }

    @Override
    protected Class<Collaudoverifiche> getEntityClass() {

	return Collaudoverifiche.class;
    }

    @Override
    public List<Collaudoverifiche> findAll(Integer firstResult, Integer maxResult) {

	return collaudoverificheDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Collaudoverifiche entity) {

	if (validateEntity(entity)) {
	    collaudoverificheDAO.insert(entity);
	}
    }

    @Override
    public Collaudoverifiche findById(CollaudoverificheId id) {

	return collaudoverificheDAO.findById(id);
    }

    @Override
    public void update(Collaudoverifiche entity) {

	if (validateEntity(entity)) {
	    collaudoverificheDAO.update(entity);
	}
    }

    @Override
    public void delete(Collaudoverifiche entity) {

	if (isDeleteAllowed(entity)) {
	    collaudoverificheDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Collaudoverifiche entity) {

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
