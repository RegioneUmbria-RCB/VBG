package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiesclusioniDAO;
import it.gruppoinit.pal.gp.core.domain.ComuniAssociatiEsclusioni;
import it.gruppoinit.pal.gp.core.domain.ComuniAssociatiEsclusioniId;

/**
 * 
 * @author
 */
public interface ComuniassociatiesclusioniService extends BaseService<ComuniAssociatiEsclusioni, ComuniAssociatiEsclusioniId> {

    /**
     * @see ComuniassociatiesclusioniDAO#findAll(Integer, Integer)
     */
    public List<ComuniAssociatiEsclusioni> findAll(Integer firstResult, Integer maxResult);

    public List<ComuniAssociatiEsclusioni> findByIdComuneandSoftware(String idcomune, String software);
}
