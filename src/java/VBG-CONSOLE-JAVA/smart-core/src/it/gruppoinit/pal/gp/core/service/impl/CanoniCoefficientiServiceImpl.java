package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniCoefficientiDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniCoefficienti;
import it.gruppoinit.pal.gp.core.domain.CanoniCoefficientiId;
import it.gruppoinit.pal.gp.core.service.CanoniCoefficientiService;

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
public class CanoniCoefficientiServiceImpl extends BaseServiceImpl<CanoniCoefficienti, CanoniCoefficientiId> implements CanoniCoefficientiService {

    private CanoniCoefficientiDAO canonicoefficientiDAO;

    @Autowired
    public void setCanoniCoefficientiDAO(CanoniCoefficientiDAO canonicoefficientiDAO) {

	this.canonicoefficientiDAO = canonicoefficientiDAO;
    }

    @Override
    protected Class<CanoniCoefficienti> getEntityClass() {

	return CanoniCoefficienti.class;
    }

    @Override
    public List<CanoniCoefficienti> findAll(Integer firstResult, Integer maxResult) {

	return canonicoefficientiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CanoniCoefficienti entity) {

	if (validateEntity(entity)) {
	    canonicoefficientiDAO.insert(entity);
	}
    }

    @Override
    public CanoniCoefficienti findById(CanoniCoefficientiId id) {

	return canonicoefficientiDAO.findById(id);
    }

    @Override
    public void update(CanoniCoefficienti entity) {

	if (validateEntity(entity)) {
	    canonicoefficientiDAO.update(entity);
	}
    }

    @Override
    public void delete(CanoniCoefficienti entity) {

	if (isDeleteAllowed(entity)) {
	    canonicoefficientiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CanoniCoefficienti entity) {

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
