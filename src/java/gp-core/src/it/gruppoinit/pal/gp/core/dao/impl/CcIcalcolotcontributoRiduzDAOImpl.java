package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcolotcontributoRiduzDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotcontributoRiduz;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcIcalcolotcontributoRiduzDAOImpl extends BaseDAOImpl<CcIcalcolotcontributoRiduz, PkId> implements CcIcalcolotcontributoRiduzDAO {

    @Override
    public Class<CcIcalcolotcontributoRiduz> getEntityClass() {

	return CcIcalcolotcontributoRiduz.class;
    }

    @Override
    public List<CcIcalcolotcontributoRiduz> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
