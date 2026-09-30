package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.DomandefrontalberoDAO;
import it.gruppoinit.pal.gp.core.domain.Domandefrontalbero;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.DomandefrontalberoService;

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
public class DomandefrontalberoServiceImpl extends BaseServiceImpl<Domandefrontalbero, PkId> implements DomandefrontalberoService {

    private DomandefrontalberoDAO domandefrontalberoDAO;

    @Autowired
    public void setDomandefrontalberoDAO(DomandefrontalberoDAO domandefrontalberoDAO) {

	this.domandefrontalberoDAO = domandefrontalberoDAO;
    }

    @Override
    protected Class<Domandefrontalbero> getEntityClass() {

	return Domandefrontalbero.class;
    }

    @Override
    public List<Domandefrontalbero> findAll(Integer firstResult, Integer maxResult) {

	return domandefrontalberoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Domandefrontalbero entity) {

	if (validateEntity(entity)) {
	    domandefrontalberoDAO.insert(entity);
	}
    }

    @Override
    public Domandefrontalbero findById(PkId id) {

	return domandefrontalberoDAO.findById(id);
    }

    @Override
    public void update(Domandefrontalbero entity) {

	if (validateEntity(entity)) {
	    domandefrontalberoDAO.update(entity);
	}
    }

    @Override
    public void delete(Domandefrontalbero entity) {

	if (isDeleteAllowed(entity)) {
	    domandefrontalberoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Domandefrontalbero entity) {

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
