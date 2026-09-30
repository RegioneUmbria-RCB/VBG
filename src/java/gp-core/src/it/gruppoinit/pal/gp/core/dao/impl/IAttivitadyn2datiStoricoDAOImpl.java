package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2datiStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiStoricoId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IAttivitadyn2datiStoricoDAOImpl extends BaseDAOImpl<IAttivitadyn2datiStorico, IAttivitadyn2datiStoricoId> implements
	IAttivitadyn2datiStoricoDAO {

    @Override
    public Class<IAttivitadyn2datiStorico> getEntityClass() {

	return IAttivitadyn2datiStorico.class;
    }

    @Override
    public List<IAttivitadyn2datiStorico> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
