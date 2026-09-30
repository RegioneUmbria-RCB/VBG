package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentihummingbirdDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Movimentihummingbird;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MovimentihummingbirdDAOImpl extends BaseDAOImpl<Movimentihummingbird, PkId> implements MovimentihummingbirdDAO {

    @Override
    public Class<Movimentihummingbird> getEntityClass() {

	return Movimentihummingbird.class;
    }

    @Override
    public List<Movimentihummingbird> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }
}
