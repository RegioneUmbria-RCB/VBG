package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.SubprocedureDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Subprocedure;
import it.gruppoinit.pal.gp.core.service.SubprocedureService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class SubprocedureServiceImpl extends BaseServiceImpl<Subprocedure, PkId> implements SubprocedureService {

    private SubprocedureDAO subprocedureDAO;

    @Autowired
    public void setSubprocedureDAO(SubprocedureDAO subprocedureDAO) {

	this.subprocedureDAO = subprocedureDAO;
    }

    @Override
    protected Class<Subprocedure> getEntityClass() {

	return Subprocedure.class;
    }

    @Override
    public List<Subprocedure> findAll(Integer firstResult, Integer maxResult) {

	return subprocedureDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Subprocedure entity) {

	if (validateEntity(entity)) {
	    subprocedureDAO.insert(entity);
	}
    }

    @Override
    public Subprocedure findById(PkId id) {

	return subprocedureDAO.findById(id);
    }

    @Override
    public void update(Subprocedure entity) {

	if (validateEntity(entity)) {
	    subprocedureDAO.update(entity);
	}
    }

    @Override
    public void delete(Subprocedure entity) {

	if (isDeleteAllowed(entity)) {
	    subprocedureDAO.delete(entity);
	}
    }
    // protected boolean isDeleteAllowed(Subprocedure entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
}
