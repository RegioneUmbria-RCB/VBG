package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.DomandefrontDAO;
import it.gruppoinit.pal.gp.core.domain.Domandefront;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.DomandefrontService;

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
public class DomandefrontServiceImpl extends BaseServiceImpl<Domandefront, PkId> implements DomandefrontService {

    private DomandefrontDAO domandefrontDAO;

    @Autowired
    public void setDomandefrontDAO(DomandefrontDAO domandefrontDAO) {

	this.domandefrontDAO = domandefrontDAO;
    }

    @Override
    protected Class<Domandefront> getEntityClass() {

	return Domandefront.class;
    }

    @Override
    public List<Domandefront> findAll(Integer firstResult, Integer maxResult) {

	return domandefrontDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Domandefront entity) {

	if (validateEntity(entity)) {
	    domandefrontDAO.insert(entity);
	}
    }

    @Override
    public Domandefront findById(PkId id) {

	return domandefrontDAO.findById(id);
    }

    @Override
    public void update(Domandefront entity) {

	if (validateEntity(entity)) {
	    domandefrontDAO.update(entity);
	}
    }

    @Override
    public void delete(Domandefront entity) {

	if (isDeleteAllowed(entity)) {
	    domandefrontDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Domandefront entity) {

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
