package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ArchiviazioniIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface ArchiviazioniIstanzeDAO extends BaseDAO<ArchiviazioniIstanze, PkId> {

    /**
     * metodo non utilizzato
     * 
     */
    public List<ArchiviazioniIstanze> findAll(Integer firstResult, Integer maxResult);

    public Integer insert(Integer codiceArchiviazione, Integer codiceIstanza);
}
