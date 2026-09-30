package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.ComuniAssociatiEsclusioni;
import it.gruppoinit.pal.gp.core.domain.ComuniAssociatiEsclusioniId;

/**
 * 
 * @author
 */
public interface ComuniassociatiesclusioniDAO extends BaseDAO<ComuniAssociatiEsclusioni, ComuniAssociatiEsclusioniId> {

    public List<ComuniAssociatiEsclusioni> findAll(Integer firstResult, Integer maxResult);
}
