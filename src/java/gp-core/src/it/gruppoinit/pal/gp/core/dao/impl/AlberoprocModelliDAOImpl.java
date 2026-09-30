package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocModelliDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocModelli;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class AlberoprocModelliDAOImpl extends BaseDAOImpl<AlberoprocModelli, PkId> implements AlberoprocModelliDAO {

    @Override
    public Class<AlberoprocModelli> getEntityClass() {

	return AlberoprocModelli.class;
    }
}
