package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.StpModalitaAperturaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.TipoSchedaEndo;
import it.gruppoinit.pal.gp.core.domain.StpModalitaApertura;

import java.util.List;

/**
 * 
 * @author
 */
public interface StpModalitaAperturaService extends BaseService<StpModalitaApertura, String> {

    /**
     * @see StpModalitaAperturaDAO#findAll(Integer, Integer)
     */
    public List<StpModalitaApertura> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera le nature endo filtrando per tipo scheda endo
     * 
     * @param schedaTipoEndo1
     * @return
     */
    public List<StpModalitaApertura> findStpModalitaByTipoScheda(TipoSchedaEndo schedaTipoEndo1);
}
