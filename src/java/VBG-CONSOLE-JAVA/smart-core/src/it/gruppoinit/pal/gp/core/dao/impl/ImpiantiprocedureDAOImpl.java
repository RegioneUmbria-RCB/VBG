package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ImpiantiprocedureDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Impiantiprocedure;
import it.gruppoinit.pal.gp.core.domain.ImpiantiprocedureId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class ImpiantiprocedureDAOImpl extends BaseDAOImpl<Impiantiprocedure, ImpiantiprocedureId> implements ImpiantiprocedureDAO {

    @Override
    public Class<Impiantiprocedure> getEntityClass() {

	return Impiantiprocedure.class;
    }

    @Override
    public List<Impiantiprocedure> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }
}
