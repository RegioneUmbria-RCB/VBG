package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomRichiesteDAO;
import it.gruppoinit.pal.gp.core.domain.FoDomRichieste;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class FoDomRichiesteDAOImpl extends BaseDAOImpl<FoDomRichieste, PkId> implements FoDomRichiesteDAO {

    @Override
    public Class<FoDomRichieste> getEntityClass() {

	return FoDomRichieste.class;
    }
}
