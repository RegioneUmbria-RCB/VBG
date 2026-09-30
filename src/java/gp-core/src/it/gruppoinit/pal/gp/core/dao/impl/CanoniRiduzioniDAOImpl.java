package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniRiduzioniDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CanoniRiduzioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CanoniRiduzioniDAOImpl extends BaseDAOImpl<CanoniRiduzioni, PkId> implements CanoniRiduzioniDAO {

    @Override
    public Class<CanoniRiduzioni> getEntityClass() {

	return CanoniRiduzioni.class;
    }

    @Override
    public List<CanoniRiduzioni> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
