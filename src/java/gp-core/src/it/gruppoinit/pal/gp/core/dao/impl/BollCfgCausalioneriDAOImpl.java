package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.BollCfgCausalioneriDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneri;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneriId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class BollCfgCausalioneriDAOImpl extends BaseDAOImpl<BollCfgCausalioneri, BollCfgCausalioneriId> implements BollCfgCausalioneriDAO {

    @Override
    public Class<BollCfgCausalioneri> getEntityClass() {

	return BollCfgCausalioneri.class;
    }

    @Override
    public List<BollCfgCausalioneri> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	//return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY, DAOOrderTypeEnum.ASC);
    }
}
