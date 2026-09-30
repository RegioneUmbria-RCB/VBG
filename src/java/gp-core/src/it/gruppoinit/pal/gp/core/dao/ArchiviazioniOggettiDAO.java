package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ArchiviazioniOggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface ArchiviazioniOggettiDAO extends BaseDAO<ArchiviazioniOggetti, PkId> {

    /**
     * metodo non utilizzato
     * 
     */
    public List<ArchiviazioniOggetti> findAll(Integer firstResult, Integer maxResult);

    public void insert(Integer codiceOggetto, Integer codiceArchiviazioniIstanze);
}
