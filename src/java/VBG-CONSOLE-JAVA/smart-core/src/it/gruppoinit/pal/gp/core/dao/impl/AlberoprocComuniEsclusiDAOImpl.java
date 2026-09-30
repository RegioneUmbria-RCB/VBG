package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AlberoprocComuniEsclusiDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocComuniEsclusi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocComuniEsclusiId;

@Repository
public class AlberoprocComuniEsclusiDAOImpl extends BaseDAOImpl<AlberoprocComuniEsclusi, AlberoprocComuniEsclusiId>
	implements AlberoprocComuniEsclusiDAO {

    @Override
    public Class<AlberoprocComuniEsclusi> getEntityClass() {

	return AlberoprocComuniEsclusi.class;
    }
}
