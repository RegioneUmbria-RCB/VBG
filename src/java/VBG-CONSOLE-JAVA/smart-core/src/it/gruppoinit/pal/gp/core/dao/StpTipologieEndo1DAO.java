package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;

public interface StpTipologieEndo1DAO extends BaseDAO<StpTipologieEndo1, PkId> {

    public StpTipologieEndo1 findbyStpCodice(String idcomune, Integer stpCodice);

    public StpTipologieEndo1 findbyTipifamiglieendo(Tipifamiglieendo tipifamiglieendo);

    /**
     * torna una lista di record ordinati per codiceStp
     */
    public List<StpTipologieEndo1> findAll(Integer firstResult, Integer maxResult);
}
