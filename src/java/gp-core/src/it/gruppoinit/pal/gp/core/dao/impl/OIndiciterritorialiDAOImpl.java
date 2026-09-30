package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OIndiciterritorialiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.OIndiciterritoriali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OIndiciterritorialiDAOImpl extends BaseDAOImpl<OIndiciterritoriali, PkId> implements OIndiciterritorialiDAO {

    @Override
    public Class<OIndiciterritoriali> getEntityClass() {

	return OIndiciterritoriali.class;
    }

    @Override
    public List<OIndiciterritoriali> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
