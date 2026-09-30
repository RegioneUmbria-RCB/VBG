package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CollaudoDAO;
import it.gruppoinit.pal.gp.core.domain.Collaudo;
import it.gruppoinit.pal.gp.core.domain.CollaudoId;
import it.gruppoinit.pal.gp.core.service.CollaudoService;

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
public class CollaudoServiceImpl extends BaseServiceImpl<Collaudo, CollaudoId> implements CollaudoService {

    private CollaudoDAO collaudoDAO;

    @Autowired
    public void setCollaudoDAO(CollaudoDAO collaudoDAO) {

	this.collaudoDAO = collaudoDAO;
    }

    @Override
    protected Class<Collaudo> getEntityClass() {

	return Collaudo.class;
    }

    @Override
    public List<Collaudo> findAll(Integer firstResult, Integer maxResult) {

	return collaudoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Collaudo entity) {

	if (validateEntity(entity)) {
	    collaudoDAO.insert(entity);
	}
    }

    @Override
    public Collaudo findById(CollaudoId id) {

	return collaudoDAO.findById(id);
    }

    @Override
    public void update(Collaudo entity) {

	if (validateEntity(entity)) {
	    collaudoDAO.update(entity);
	}
    }

    @Override
    public void delete(Collaudo entity) {

	if (isDeleteAllowed(entity)) {
	    collaudoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Collaudo entity) {

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
