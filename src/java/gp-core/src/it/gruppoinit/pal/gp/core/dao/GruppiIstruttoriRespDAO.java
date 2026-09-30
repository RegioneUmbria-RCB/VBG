package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.GruppiIstruttoriResp;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface GruppiIstruttoriRespDAO extends BaseDAO<GruppiIstruttoriResp, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<GruppiIstruttoriResp> findAll(Integer firstResult, Integer maxResult);
}
