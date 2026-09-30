package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcCoeffcontributoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CcCoeffcontributo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcCoeffcontributoDAOImpl extends BaseDAOImpl<CcCoeffcontributo, PkId> implements CcCoeffcontributoDAO {

    @Override
    public Class<CcCoeffcontributo> getEntityClass() {

	return CcCoeffcontributo.class;
    }

    @Override
    public List<CcCoeffcontributo> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
