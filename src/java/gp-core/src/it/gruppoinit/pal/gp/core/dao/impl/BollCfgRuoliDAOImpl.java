package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.BollCfgRuoliDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoli;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoliId;

/**
 * 
 * @author
 */
@Repository
public class BollCfgRuoliDAOImpl extends BaseDAOImpl<BollCfgRuoli, BollCfgRuoliId> implements BollCfgRuoliDAO {

    @Override
    public Class<BollCfgRuoli> getEntityClass() {

	return BollCfgRuoli.class;
    }

    @Override
    public List<BollCfgRuoli> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	//return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY, DAOOrderTypeEnum.ASC);
    }
}
