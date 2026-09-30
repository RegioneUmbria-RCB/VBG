package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniConfigareeDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigaree;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CanoniConfigareeService;

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
public class CanoniConfigareeServiceImpl extends BaseServiceImpl<CanoniConfigaree, PkId> implements CanoniConfigareeService {

    private CanoniConfigareeDAO canoniconfigareeDAO;

    @Autowired
    public void setCanoniConfigareeDAO(CanoniConfigareeDAO canoniconfigareeDAO) {

	this.canoniconfigareeDAO = canoniconfigareeDAO;
    }

    @Override
    protected Class<CanoniConfigaree> getEntityClass() {

	return CanoniConfigaree.class;
    }

    @Override
    public List<CanoniConfigaree> findAll(Integer firstResult, Integer maxResult) {

	return canoniconfigareeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CanoniConfigaree entity) {

	if (validateEntity(entity)) {
	    canoniconfigareeDAO.insert(entity);
	}
    }

    @Override
    public CanoniConfigaree findById(PkId id) {

	return canoniconfigareeDAO.findById(id);
    }

    @Override
    public void update(CanoniConfigaree entity) {

	if (validateEntity(entity)) {
	    canoniconfigareeDAO.update(entity);
	}
    }

    @Override
    public void delete(CanoniConfigaree entity) {

	if (isDeleteAllowed(entity)) {
	    canoniconfigareeDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CanoniConfigaree entity) {

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
