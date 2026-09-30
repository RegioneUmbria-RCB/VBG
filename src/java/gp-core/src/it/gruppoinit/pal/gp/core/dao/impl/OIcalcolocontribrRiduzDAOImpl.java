package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OIcalcolocontribrRiduzDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribrRiduz;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OIcalcolocontribrRiduzDAOImpl extends BaseDAOImpl<OIcalcolocontribrRiduz, PkId> implements OIcalcolocontribrRiduzDAO {

    @Override
    public Class<OIcalcolocontribrRiduz> getEntityClass() {

	return OIcalcolocontribrRiduz.class;
    }

    @Override
    public List<OIcalcolocontribrRiduz> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
