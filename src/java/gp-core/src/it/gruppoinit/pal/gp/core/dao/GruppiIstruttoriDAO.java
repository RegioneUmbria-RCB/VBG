package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface GruppiIstruttoriDAO extends BaseDAO<GruppiIstruttori, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<GruppiIstruttori> findAll(Integer firstResult, Integer maxResult);

    public List<GruppiIstruttori> findByDescrizione(String textToSearch);
}
