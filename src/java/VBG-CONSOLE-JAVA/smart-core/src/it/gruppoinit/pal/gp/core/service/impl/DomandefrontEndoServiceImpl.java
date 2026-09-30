package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.DomandefrontEndoDAO;
import it.gruppoinit.pal.gp.core.domain.DomandefrontEndo;
import it.gruppoinit.pal.gp.core.domain.DomandefrontEndoId;
import it.gruppoinit.pal.gp.core.service.DomandefrontEndoService;

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
public class DomandefrontEndoServiceImpl extends BaseServiceImpl<DomandefrontEndo, DomandefrontEndoId> implements DomandefrontEndoService {

    private DomandefrontEndoDAO domandefrontendoDAO;

    @Autowired
    public void setDomandefrontEndoDAO(DomandefrontEndoDAO domandefrontendoDAO) {

	this.domandefrontendoDAO = domandefrontendoDAO;
    }

    @Override
    protected Class<DomandefrontEndo> getEntityClass() {

	return DomandefrontEndo.class;
    }

    @Override
    public List<DomandefrontEndo> findAll(Integer firstResult, Integer maxResult) {

	return domandefrontendoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(DomandefrontEndo entity) {

	if (validateEntity(entity)) {
	    domandefrontendoDAO.insert(entity);
	}
    }

    @Override
    public DomandefrontEndo findById(DomandefrontEndoId id) {

	return domandefrontendoDAO.findById(id);
    }

    @Override
    public void update(DomandefrontEndo entity) {

	if (validateEntity(entity)) {
	    domandefrontendoDAO.update(entity);
	}
    }

    @Override
    public void delete(DomandefrontEndo entity) {

	if (isDeleteAllowed(entity)) {
	    domandefrontendoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(DomandefrontEndo entity) {

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
