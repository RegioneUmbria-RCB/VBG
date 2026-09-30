package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.SdecomuniassociatiDAO;
import it.gruppoinit.pal.gp.core.domain.Sdecomuniassociati;
import it.gruppoinit.pal.gp.core.domain.SdecomuniassociatiId;

@Repository
public class SdecomuniassociatiDAOImpl extends BaseDAOImpl<Sdecomuniassociati, SdecomuniassociatiId> implements SdecomuniassociatiDAO {

    @Override
    public Class<Sdecomuniassociati> getEntityClass() {

	return Sdecomuniassociati.class;
    }
}
