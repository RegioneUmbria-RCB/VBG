package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OIcalcolocontribtBtoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribtBto;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OIcalcolocontribtBtoDAOImpl extends BaseDAOImpl<OIcalcolocontribtBto, PkId> implements OIcalcolocontribtBtoDAO {

    @Override
    public Class<OIcalcolocontribtBto> getEntityClass() {

	return OIcalcolocontribtBto.class;
    }

    @Override
    public List<OIcalcolocontribtBto> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
