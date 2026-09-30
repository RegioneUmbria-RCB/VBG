package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OIcalcolocontribrDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.OIcalcolocontribr;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OIcalcolocontribrDAOImpl extends BaseDAOImpl<OIcalcolocontribr, PkId> implements OIcalcolocontribrDAO {

    @Override
    public Class<OIcalcolocontribr> getEntityClass() {

	return OIcalcolocontribr.class;
    }

    @Override
    public List<OIcalcolocontribr> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
