package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipiaffissioniformatiDAO;
import it.gruppoinit.pal.gp.core.domain.Tipiaffissioniformati;
import it.gruppoinit.pal.gp.core.domain.TipiaffissioniformatiId;
import it.gruppoinit.pal.gp.core.service.TipiaffissioniformatiService;

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
public class TipiaffissioniformatiServiceImpl extends BaseServiceImpl<Tipiaffissioniformati, TipiaffissioniformatiId> implements
	TipiaffissioniformatiService {

    private TipiaffissioniformatiDAO tipiaffissioniformatiDAO;

    @Autowired
    public void setTipiaffissioniformatiDAO(TipiaffissioniformatiDAO tipiaffissioniformatiDAO) {

	this.tipiaffissioniformatiDAO = tipiaffissioniformatiDAO;
    }

    @Override
    protected Class<Tipiaffissioniformati> getEntityClass() {

	return Tipiaffissioniformati.class;
    }

    @Override
    public List<Tipiaffissioniformati> findAll(Integer firstResult, Integer maxResult) {

	return tipiaffissioniformatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipiaffissioniformati entity) {

	if (validateEntity(entity)) {
	    tipiaffissioniformatiDAO.insert(entity);
	}
    }

    @Override
    public Tipiaffissioniformati findById(TipiaffissioniformatiId id) {

	return tipiaffissioniformatiDAO.findById(id);
    }

    @Override
    public void update(Tipiaffissioniformati entity) {

	if (validateEntity(entity)) {
	    tipiaffissioniformatiDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipiaffissioniformati entity) {

	if (isDeleteAllowed(entity)) {
	    tipiaffissioniformatiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Tipiaffissioniformati entity) {

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
