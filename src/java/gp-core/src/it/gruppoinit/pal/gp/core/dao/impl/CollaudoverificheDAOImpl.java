package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CollaudoverificheDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Collaudoverifiche;
import it.gruppoinit.pal.gp.core.domain.CollaudoverificheId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CollaudoverificheDAOImpl extends BaseDAOImpl<Collaudoverifiche, CollaudoverificheId> implements CollaudoverificheDAO {

    @Override
    public Class<Collaudoverifiche> getEntityClass() {

	return Collaudoverifiche.class;
    }

    @Override
    public List<Collaudoverifiche> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
