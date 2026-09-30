package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocEndoLocDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoLoc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class AlberoprocEndoLocDAOImpl extends BaseDAOImpl<AlberoprocEndoLoc, PkId> implements AlberoprocEndoLocDAO {

    @Override
    public Class<AlberoprocEndoLoc> getEntityClass() {

	return AlberoprocEndoLoc.class;
    }
}
