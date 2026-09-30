package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.BatchScadIstanzeDAO;
import it.gruppoinit.pal.gp.core.domain.BatchScadIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BatchScadIstanzeService;

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
public class BatchScadIstanzeServiceImpl extends BaseServiceImpl<BatchScadIstanze, PkId> implements BatchScadIstanzeService {

    private BatchScadIstanzeDAO batchscadistanzeDAO;

    @Autowired
    public void setBatchScadIstanzeDAO(BatchScadIstanzeDAO batchscadistanzeDAO) {

	this.batchscadistanzeDAO = batchscadistanzeDAO;
    }

    @Override
    protected Class<BatchScadIstanze> getEntityClass() {

	return BatchScadIstanze.class;
    }

    @Override
    public List<BatchScadIstanze> findAll(Integer firstResult, Integer maxResult) {

	return batchscadistanzeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(BatchScadIstanze entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    batchscadistanzeDAO.insert(entity);
	}
    }

    @Override
    public BatchScadIstanze findById(PkId id) {

	return batchscadistanzeDAO.findById(id);
    }

    @Override
    public void update(BatchScadIstanze entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    batchscadistanzeDAO.update(entity);
	}
    }

    private void dataIntegration(BatchScadIstanze entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro BatchScadIstanze è nullo");
	}
    }

    @Override
    public void delete(BatchScadIstanze entity) {

	if (isDeleteAllowed(entity)) {
	    batchscadistanzeDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BatchScadIstanze entity) {

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
