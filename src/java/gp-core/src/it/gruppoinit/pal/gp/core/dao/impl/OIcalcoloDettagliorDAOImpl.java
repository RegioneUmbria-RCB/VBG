package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OIcalcoloDettagliorDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.OIcalcoloDettaglior;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OIcalcoloDettagliorDAOImpl extends BaseDAOImpl<OIcalcoloDettaglior, PkId> implements OIcalcoloDettagliorDAO {

    @Override
    public Class<OIcalcoloDettaglior> getEntityClass() {

	return OIcalcoloDettaglior.class;
    }

    @Override
    public List<OIcalcoloDettaglior> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
