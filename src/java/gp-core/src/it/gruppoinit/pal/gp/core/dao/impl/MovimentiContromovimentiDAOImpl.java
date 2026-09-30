package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentiContromovimentiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MovimentiContromovimentiDAOImpl extends BaseDAOImpl<MovimentiContromovimenti, PkId> implements MovimentiContromovimentiDAO {

    @Override
    public Class<MovimentiContromovimenti> getEntityClass() {

	return MovimentiContromovimenti.class;
    }

    @Override
    public List<MovimentiContromovimenti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }
}
