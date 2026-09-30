package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.NatureProcedureDAO;
import it.gruppoinit.pal.gp.core.domain.NatureProcedure;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class NatureProcedureDAOImpl extends BaseDAOImpl<NatureProcedure, PkId> implements NatureProcedureDAO {

    @Override
    public Class<NatureProcedure> getEntityClass() {

	return NatureProcedure.class;
    }
}
