package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoDomandeOggettiDAO;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggettiId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoDomandeOggettiService extends BaseService<FoDomandeOggetti, FoDomandeOggettiId> {

    /**
     * @see FoDomandeOggettiDAO#findAll(Integer, Integer)
     */
    public List<FoDomandeOggetti> findAll(Integer firstResult, Integer maxResult);

    public List<FoDomandeOggetti> findByIdDomandaFo(Integer idDomandaFo);
}
