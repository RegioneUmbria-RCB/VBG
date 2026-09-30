package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoloDcontributoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontributo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcIcalcoloDcontributoDAOImpl extends BaseDAOImpl<CcIcalcoloDcontributo, PkId> implements CcIcalcoloDcontributoDAO {

    @Override
    public Class<CcIcalcoloDcontributo> getEntityClass() {

	return CcIcalcoloDcontributo.class;
    }

    @Override
    public List<CcIcalcoloDcontributo> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
