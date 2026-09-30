package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;

import java.util.List;

public interface ComuniassociatiDAO extends BaseDAO<Comuniassociati, ComuniassociatiId> {

    /**
     * torna la lista dei comuni associati ordinati per COMUNE ASC
     * 
     * @return
     */
    public List<Comuniassociati> findAll();
}
