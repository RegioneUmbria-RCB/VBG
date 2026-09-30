package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDyn2modellitDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellitId;

import org.springframework.stereotype.Repository;

@Repository
public class AlberoprocDyn2modellitDAOImpl extends BaseDAOImpl<AlberoprocDyn2modellit, AlberoprocDyn2modellitId> implements AlberoprocDyn2modellitDAO {

    @Override
    public Class<AlberoprocDyn2modellit> getEntityClass() {

	return AlberoprocDyn2modellit.class;
    }
}
