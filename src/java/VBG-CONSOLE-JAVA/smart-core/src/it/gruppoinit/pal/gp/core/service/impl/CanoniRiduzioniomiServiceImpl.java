package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniRiduzioniomiDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniRiduzioniomi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CanoniRiduzioniomiService;

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
public class CanoniRiduzioniomiServiceImpl extends BaseServiceImpl<CanoniRiduzioniomi, PkId> implements CanoniRiduzioniomiService {

    private CanoniRiduzioniomiDAO canoniriduzioniomiDAO;

    @Autowired
    public void setCanoniRiduzioniomiDAO(CanoniRiduzioniomiDAO canoniriduzioniomiDAO) {

	this.canoniriduzioniomiDAO = canoniriduzioniomiDAO;
    }

    @Override
    protected Class<CanoniRiduzioniomi> getEntityClass() {

	return CanoniRiduzioniomi.class;
    }

    @Override
    public List<CanoniRiduzioniomi> findAll(Integer firstResult, Integer maxResult) {

	return canoniriduzioniomiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CanoniRiduzioniomi entity) {

	if (validateEntity(entity)) {
	    canoniriduzioniomiDAO.insert(entity);
	}
    }

    @Override
    public CanoniRiduzioniomi findById(PkId id) {

	return canoniriduzioniomiDAO.findById(id);
    }

    @Override
    public void update(CanoniRiduzioniomi entity) {

	if (validateEntity(entity)) {
	    canoniriduzioniomiDAO.update(entity);
	}
    }

    @Override
    public void delete(CanoniRiduzioniomi entity) {

	if (isDeleteAllowed(entity)) {
	    canoniriduzioniomiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CanoniRiduzioniomi entity) {

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
