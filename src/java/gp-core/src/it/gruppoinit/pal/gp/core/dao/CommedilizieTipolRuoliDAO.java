package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.CommedilizieTipolRuoli;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipolRuoliId;

public interface CommedilizieTipolRuoliDAO extends BaseDAO<CommedilizieTipolRuoli, CommedilizieTipolRuoliId> {

    public List<CommedilizieTipolRuoli> findByTipologia(Integer idTipologia);

    public List<CommedilizieTipolRuoli> findByRuolo(Integer idRuolo);
}
