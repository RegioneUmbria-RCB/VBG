package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoloTcontributoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcIcalcoloTcontributoDAOImpl extends BaseDAOImpl<CcIcalcoloTcontributo, PkId> implements CcIcalcoloTcontributoDAO {

    @Override
    public Class<CcIcalcoloTcontributo> getEntityClass() {

	return CcIcalcoloTcontributo.class;
    }

    @Override
    public List<CcIcalcoloTcontributo> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
