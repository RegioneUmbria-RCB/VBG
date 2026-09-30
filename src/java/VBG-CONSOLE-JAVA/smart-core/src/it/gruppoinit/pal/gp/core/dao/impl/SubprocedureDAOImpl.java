package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.SubprocedureDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Subprocedure;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class SubprocedureDAOImpl extends BaseDAOImpl<Subprocedure, PkId> implements SubprocedureDAO {

    @Override
    public Class<Subprocedure> getEntityClass() {

	return Subprocedure.class;
    }

    @Override
    public List<Subprocedure> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
