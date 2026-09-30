package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IAttivitaTipologieDAO;
import it.gruppoinit.pal.gp.core.domain.IAttivitaTipologie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IAttivitaTipologieService extends BaseService<IAttivitaTipologie, PkId> {

    /**
     * @see IAttivitaTipologieDAO#findAll(Integer, Integer)
     */
    public List<IAttivitaTipologie> findAll(Integer firstResult, Integer maxResult);

    public List<IAttivitaTipologie> findByDescrizione(String descrizione);

    public List<IAttivitaTipologie> findByDescrizioneAndSoftware(String textToSearch, String paramSoftware);
}
