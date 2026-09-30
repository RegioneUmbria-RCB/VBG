package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcCausaliriduzionirDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcCausaliriduzionirDAOImpl extends BaseDAOImpl<CcCausaliriduzionir, PkId> implements CcCausaliriduzionirDAO {

    @Override
    public Class<CcCausaliriduzionir> getEntityClass() {

	return CcCausaliriduzionir.class;
    }

    @Override
    public List<CcCausaliriduzionir> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
