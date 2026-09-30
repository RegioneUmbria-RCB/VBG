package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.StpTipologieEndo2DAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo2;

@Repository
public class StpTipologieEndo2DAOImpl extends BaseDAOImpl<StpTipologieEndo2, PkId> implements StpTipologieEndo2DAO {

    @Override
    public Class<StpTipologieEndo2> getEntityClass() {

	return StpTipologieEndo2.class;
    }
}
