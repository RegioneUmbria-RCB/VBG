package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ControlloverificheDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Controlloverifiche;
import it.gruppoinit.pal.gp.core.domain.ControlloverificheId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ControlloverificheDAOImpl extends BaseDAOImpl<Controlloverifiche, ControlloverificheId> implements ControlloverificheDAO {

    @Override
    public Class<Controlloverifiche> getEntityClass() {

	return Controlloverifiche.class;
    }

    @Override
    public List<Controlloverifiche> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
