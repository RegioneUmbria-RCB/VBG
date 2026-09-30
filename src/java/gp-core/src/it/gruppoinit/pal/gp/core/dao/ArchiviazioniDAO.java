package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Archiviazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface ArchiviazioniDAO extends BaseDAO<Archiviazioni, PkId> {

    /**
     * recupera tutte le archiviazioni ordinate per data ASC
     * 
     */
    public List<Archiviazioni> findAll(Integer firstResult, Integer maxResult);

    public List<Archiviazioni> findAll(Integer firstResult, Integer maxResult, Boolean isSoloConErrori);
}
