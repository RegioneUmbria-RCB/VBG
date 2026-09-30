package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoliDettagliotDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoliDettagliot;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcIcalcoliDettagliotDAOImpl extends BaseDAOImpl<CcIcalcoliDettagliot, PkId> implements CcIcalcoliDettagliotDAO {

    @Override
    public Class<CcIcalcoliDettagliot> getEntityClass() {

	return CcIcalcoliDettagliot.class;
    }

    @Override
    public List<CcIcalcoliDettagliot> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
