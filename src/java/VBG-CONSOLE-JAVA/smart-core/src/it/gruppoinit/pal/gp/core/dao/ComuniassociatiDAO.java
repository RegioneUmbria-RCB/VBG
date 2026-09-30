package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;

import java.util.List;

public interface ComuniassociatiDAO extends BaseDAO<Comuniassociati, ComuniassociatiId> {

    /**
     * torna la lista dei comuni associati per l'idcomune passato
     * 
     * @param idcomune
     * @return
     */
    public List<Comuniassociati> findByIdcomune(String idcomune);
}
