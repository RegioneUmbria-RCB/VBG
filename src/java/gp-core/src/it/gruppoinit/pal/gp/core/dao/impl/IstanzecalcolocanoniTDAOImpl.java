package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzecalcolocanoniTDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniT;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzecalcolocanoniTDAOImpl extends BaseDAOImpl<IstanzecalcolocanoniT, PkId> implements IstanzecalcolocanoniTDAO {

    @Override
    public Class<IstanzecalcolocanoniT> getEntityClass() {

	return IstanzecalcolocanoniT.class;
    }

    @Override
    public List<IstanzecalcolocanoniT> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
