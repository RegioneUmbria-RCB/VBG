package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2modellitStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitStoricoId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IAttivitadyn2modellitStoricoDAOImpl extends BaseDAOImpl<IAttivitadyn2modellitStorico, IAttivitadyn2modellitStoricoId> implements
	IAttivitadyn2modellitStoricoDAO {

    @Override
    public Class<IAttivitadyn2modellitStorico> getEntityClass() {

	return IAttivitadyn2modellitStorico.class;
    }

    @Override
    public List<IAttivitadyn2modellitStorico> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
