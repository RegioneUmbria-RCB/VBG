package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.FoDomRichieste;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface FoDomRichiesteDAO extends BaseDAO<FoDomRichieste, PkId> {

    @Override
    public List<FoDomRichieste> findAll(Integer firstResult, Integer maxResult);
}
