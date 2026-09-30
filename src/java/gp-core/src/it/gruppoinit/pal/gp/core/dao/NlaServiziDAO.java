package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.NlaServizi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import java.util.List;

/**
 * 
 * @author
 */
public interface NlaServiziDAO extends BaseDAO<NlaServizi, PkId> {

    /**
     * recupera la lista dei nodi NLA ordinati per descrizione
     * 
     */
    public List<NlaServizi> findAll(Integer firstResult, Integer maxResult);
}
