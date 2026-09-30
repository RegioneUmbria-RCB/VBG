package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniTipisuperficiDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniTipisuperfici;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CanoniTipisuperficiService;

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
public class CanoniTipisuperficiServiceImpl extends BaseServiceImpl<CanoniTipisuperfici, PkId> implements CanoniTipisuperficiService {

    private CanoniTipisuperficiDAO canonitipisuperficiDAO;

    @Autowired
    public void setCanoniTipisuperficiDAO(CanoniTipisuperficiDAO canonitipisuperficiDAO) {

	this.canonitipisuperficiDAO = canonitipisuperficiDAO;
    }

    @Override
    protected Class<CanoniTipisuperfici> getEntityClass() {

	return CanoniTipisuperfici.class;
    }

    @Override
    public List<CanoniTipisuperfici> findAll(Integer firstResult, Integer maxResult) {

	return canonitipisuperficiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CanoniTipisuperfici entity) {

	if (validateEntity(entity)) {
	    canonitipisuperficiDAO.insert(entity);
	}
    }

    @Override
    public CanoniTipisuperfici findById(PkId id) {

	return canonitipisuperficiDAO.findById(id);
    }

    @Override
    public void update(CanoniTipisuperfici entity) {

	if (validateEntity(entity)) {
	    canonitipisuperficiDAO.update(entity);
	}
    }

    @Override
    public void delete(CanoniTipisuperfici entity) {

	if (isDeleteAllowed(entity)) {
	    canonitipisuperficiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CanoniTipisuperfici entity) {

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
