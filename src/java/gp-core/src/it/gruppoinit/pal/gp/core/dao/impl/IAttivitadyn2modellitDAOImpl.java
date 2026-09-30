package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IAttivitadyn2modellitDAOImpl extends BaseDAOImpl<IAttivitadyn2modellit, IAttivitadyn2modellitId> implements IAttivitadyn2modellitDAO {

    @Override
    public Class<IAttivitadyn2modellit> getEntityClass() {

	return IAttivitadyn2modellit.class;
    }

    @Override
    public List<IAttivitadyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
