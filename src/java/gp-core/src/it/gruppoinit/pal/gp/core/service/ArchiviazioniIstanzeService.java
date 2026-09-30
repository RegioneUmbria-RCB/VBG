package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ArchiviazioniIstanzeDAO;
import it.gruppoinit.pal.gp.core.domain.ArchiviazioniIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface ArchiviazioniIstanzeService extends BaseService<ArchiviazioniIstanze, PkId> {

    /**
     * @see ArchiviazioniIstanzeDAO#findAll(Integer, Integer)
     */
    public List<ArchiviazioniIstanze> findAll(Integer firstResult, Integer maxResult);

    public Integer insert(Integer codiceArchiviazione, Integer codiceIstanza);

    public List<ArchiviazioniIstanze> findByIstanza(Integer codiceIstanza);

    public void deleteByIstanza(Integer codiceIstanza);

    public void evict(ArchiviazioniIstanze entity);

    public List<ArchiviazioniIstanze> findEscluse(Integer codiceArchiviazione);

    public List<ArchiviazioniIstanze> findIstanzaOggettoArchiviato(Integer codiceArchiviazione);
}
