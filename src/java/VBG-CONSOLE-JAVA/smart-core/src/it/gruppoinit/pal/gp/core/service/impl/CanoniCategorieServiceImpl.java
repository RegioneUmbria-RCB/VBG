package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniCategorieDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CanoniCategorieService;

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
public class CanoniCategorieServiceImpl extends BaseServiceImpl<CanoniCategorie, PkId> implements CanoniCategorieService {

    private CanoniCategorieDAO canonicategorieDAO;

    @Autowired
    public void setCanoniCategorieDAO(CanoniCategorieDAO canonicategorieDAO) {

	this.canonicategorieDAO = canonicategorieDAO;
    }

    @Override
    protected Class<CanoniCategorie> getEntityClass() {

	return CanoniCategorie.class;
    }

    @Override
    public List<CanoniCategorie> findAll(Integer firstResult, Integer maxResult) {

	return canonicategorieDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CanoniCategorie entity) {

	if (validateEntity(entity)) {
	    canonicategorieDAO.insert(entity);
	}
    }

    @Override
    public CanoniCategorie findById(PkId id) {

	return canonicategorieDAO.findById(id);
    }

    @Override
    public void update(CanoniCategorie entity) {

	if (validateEntity(entity)) {
	    canonicategorieDAO.update(entity);
	}
    }

    @Override
    public void delete(CanoniCategorie entity) {

	if (isDeleteAllowed(entity)) {
	    canonicategorieDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CanoniCategorie entity) {

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
