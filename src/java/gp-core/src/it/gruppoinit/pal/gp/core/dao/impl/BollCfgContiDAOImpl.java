package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.BollCfgContiDAO;
import it.gruppoinit.pal.gp.core.domain.BollCfgConti;
import it.gruppoinit.pal.gp.core.domain.BollCfgContiId;

@Repository
public class BollCfgContiDAOImpl extends BaseDAOImpl<BollCfgConti, BollCfgContiId> implements BollCfgContiDAO {

    @Override
    public Class<BollCfgConti> getEntityClass() {

	return BollCfgConti.class;
    }
}
