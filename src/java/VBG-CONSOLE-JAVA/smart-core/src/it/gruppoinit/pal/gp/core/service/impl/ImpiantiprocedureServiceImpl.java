package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ImpiantiprocedureDAO;
import it.gruppoinit.pal.gp.core.domain.Impiantiprocedure;
import it.gruppoinit.pal.gp.core.domain.ImpiantiprocedureId;
import it.gruppoinit.pal.gp.core.service.ImpiantiprocedureService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Riccardo Bocci
 */
@Service
public class ImpiantiprocedureServiceImpl extends BaseServiceImpl<Impiantiprocedure, ImpiantiprocedureId> implements ImpiantiprocedureService {

    private ImpiantiprocedureDAO impiantiprocedureDAO;

    @Autowired
    public void setImpiantiprocedureDAO(ImpiantiprocedureDAO impiantiprocedureDAO) {

	this.impiantiprocedureDAO = impiantiprocedureDAO;
    }

    @Override
    protected Class<Impiantiprocedure> getEntityClass() {

	return Impiantiprocedure.class;
    }

    @Override
    public List<Impiantiprocedure> findAll(Integer firstResult, Integer maxResult) {

	return impiantiprocedureDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Impiantiprocedure entity) {

	if (validateEntity(entity)) {
	    impiantiprocedureDAO.insert(entity);
	}
    }

    @Override
    public Impiantiprocedure findById(ImpiantiprocedureId id) {

	return impiantiprocedureDAO.findById(id);
    }

    @Override
    public void update(Impiantiprocedure entity) {

	if (validateEntity(entity)) {
	    impiantiprocedureDAO.update(entity);
	}
    }

    @Override
    public void delete(Impiantiprocedure entity) {

	if (isDeleteAllowed(entity)) {
	    impiantiprocedureDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Impiantiprocedure entity) {

	return true;
    }
}
