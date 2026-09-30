package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocTipisoggettoDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.PkId;
import java.util.List;

/**
 * 
 * @author
 */
public interface AlberoprocTipisoggettoService extends BaseService<AlberoprocTipisoggetto, PkId> {

    /**
     * @see AlberoprocTipisoggettoDAO#findAll(Integer, Integer)
     */
    public List<AlberoprocTipisoggetto> findAll(Integer firstResult, Integer maxResult);

    public List<AlberoprocTipisoggetto> findByAlberoprocId(Integer codiceIntervento, Integer firstResult, Integer maxResult);

    public List<AlberoprocTipisoggetto> findByTipiSoggettoId(Integer codiceTiposoggetto, Integer firstResult, Integer maxResult);
}
