package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.PermistanzeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Permistanze;
import it.gruppoinit.pal.gp.core.domain.PermistanzeId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class PermistanzeDAOImpl extends BaseDAOImpl<Permistanze, PermistanzeId> implements PermistanzeDAO {

    @Override
    public Class<Permistanze> getEntityClass() {

	return Permistanze.class;
    }

    @Override
    public List<Permistanze> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
