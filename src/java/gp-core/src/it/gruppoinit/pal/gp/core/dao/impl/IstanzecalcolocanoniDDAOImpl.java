package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzecalcolocanoniDDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IstanzecalcolocanoniD;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzecalcolocanoniDDAOImpl extends BaseDAOImpl<IstanzecalcolocanoniD, PkId> implements IstanzecalcolocanoniDDAO {

    @Override
    public Class<IstanzecalcolocanoniD> getEntityClass() {

	return IstanzecalcolocanoniD.class;
    }

    @Override
    public List<IstanzecalcolocanoniD> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
