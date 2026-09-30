package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.BollCfgMercatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercati;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercatiId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class BollCfgMercatiDAOImpl extends BaseDAOImpl<BollCfgMercati, BollCfgMercatiId> implements BollCfgMercatiDAO {

    @Override
    public Class<BollCfgMercati> getEntityClass() {

	return BollCfgMercati.class;
    }

    @Override
    public List<BollCfgMercati> findAll(Integer firstResult, Integer maxResult) {

	//throw new NotImplementedException();
	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }
}
