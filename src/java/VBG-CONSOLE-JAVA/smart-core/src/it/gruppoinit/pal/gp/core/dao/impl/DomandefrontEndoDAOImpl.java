package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.DomandefrontEndoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.DomandefrontEndo;
import it.gruppoinit.pal.gp.core.domain.DomandefrontEndoId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class DomandefrontEndoDAOImpl extends BaseDAOImpl<DomandefrontEndo, DomandefrontEndoId> implements DomandefrontEndoDAO {

    @Override
    public Class<DomandefrontEndo> getEntityClass() {

	return DomandefrontEndo.class;
    }

    @Override
    public List<DomandefrontEndo> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
