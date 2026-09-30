package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ArchiviazioniOggettiDAO;
import it.gruppoinit.pal.gp.core.domain.ArchiviazioniOggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface ArchiviazioniOggettiService extends BaseService<ArchiviazioniOggetti, PkId> {

    /**
     * @see ArchiviazioniOggettiDAO#findAll(Integer, Integer)
     */
    public List<ArchiviazioniOggetti> findAll(Integer firstResult, Integer maxResult);

    public void insert(Integer codiceOggetto, Integer codiceArchiviazioniIstanze);

    public void evict(ArchiviazioniOggetti entity);
}
