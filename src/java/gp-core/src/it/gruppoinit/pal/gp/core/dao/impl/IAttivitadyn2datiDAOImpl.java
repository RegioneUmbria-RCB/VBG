package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2datiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2dati;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IAttivitadyn2datiDAOImpl extends BaseDAOImpl<IAttivitadyn2dati, IAttivitadyn2datiId> implements IAttivitadyn2datiDAO {

    @Override
    public Class<IAttivitadyn2dati> getEntityClass() {

	return IAttivitadyn2dati.class;
    }

    @Override
    public List<IAttivitadyn2dati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
