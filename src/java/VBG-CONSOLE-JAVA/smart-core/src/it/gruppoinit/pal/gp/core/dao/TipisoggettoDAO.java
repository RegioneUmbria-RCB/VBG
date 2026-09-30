package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface TipisoggettoDAO extends BaseDAO<Tipisoggetto, PkId> {

    /**
     * Torna la lista dei record della tabella di un modulo software ordinati per la proprietà tiposoggetto dalla A alla
     * Z
     * 
     */
    public List<Tipisoggetto> findAll(Integer firstResult, Integer maxResult);
}
