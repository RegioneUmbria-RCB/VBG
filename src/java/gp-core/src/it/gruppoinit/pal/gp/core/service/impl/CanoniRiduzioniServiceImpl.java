package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniRiduzioniDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniRiduzioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CanoniRiduzioniService;

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
public class CanoniRiduzioniServiceImpl extends BaseServiceImpl<CanoniRiduzioni, PkId> implements CanoniRiduzioniService {

    private CanoniRiduzioniDAO canoniriduzioniDAO;

    @Autowired
    public void setCanoniRiduzioniDAO(CanoniRiduzioniDAO canoniriduzioniDAO) {

	this.canoniriduzioniDAO = canoniriduzioniDAO;
    }

    @Override
    protected Class<CanoniRiduzioni> getEntityClass() {

	return CanoniRiduzioni.class;
    }

    @Override
    public List<CanoniRiduzioni> findAll(Integer firstResult, Integer maxResult) {

	return canoniriduzioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CanoniRiduzioni entity) {

	if (validateEntity(entity)) {
	    canoniriduzioniDAO.insert(entity);
	}
    }

    @Override
    public CanoniRiduzioni findById(PkId id) {

	return canoniriduzioniDAO.findById(id);
    }

    @Override
    public void update(CanoniRiduzioni entity) {

	if (validateEntity(entity)) {
	    canoniriduzioniDAO.update(entity);
	}
    }

    @Override
    public void delete(CanoniRiduzioni entity) {

	if (isDeleteAllowed(entity)) {
	    canoniriduzioniDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CanoniRiduzioni entity) {

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
