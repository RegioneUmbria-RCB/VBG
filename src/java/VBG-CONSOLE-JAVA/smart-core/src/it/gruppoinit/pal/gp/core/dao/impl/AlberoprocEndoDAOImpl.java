package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocEndoDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;

import org.springframework.stereotype.Repository;

@Repository
public class AlberoprocEndoDAOImpl extends BaseDAOImpl<AlberoprocEndo, AlberoprocEndoId> implements AlberoprocEndoDAO {

    @Override
    public Class<AlberoprocEndo> getEntityClass() {

	return AlberoprocEndo.class;
    }
}
