package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OConfigurazionetipionereDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.OConfigurazionetipionere;
import it.gruppoinit.pal.gp.core.domain.OConfigurazionetipionereId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OConfigurazionetipionereDAOImpl extends BaseDAOImpl<OConfigurazionetipionere, OConfigurazionetipionereId> implements
	OConfigurazionetipionereDAO {

    @Override
    public Class<OConfigurazionetipionere> getEntityClass() {

	return OConfigurazionetipionere.class;
    }

    @Override
    public List<OConfigurazionetipionere> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
