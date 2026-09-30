package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiaffissioniformatiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Tipiaffissioniformati;
import it.gruppoinit.pal.gp.core.domain.TipiaffissioniformatiId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipiaffissioniformatiDAOImpl extends BaseDAOImpl<Tipiaffissioniformati, TipiaffissioniformatiId> implements TipiaffissioniformatiDAO {

    @Override
    public Class<Tipiaffissioniformati> getEntityClass() {

	return Tipiaffissioniformati.class;
    }

    @Override
    public List<Tipiaffissioniformati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
