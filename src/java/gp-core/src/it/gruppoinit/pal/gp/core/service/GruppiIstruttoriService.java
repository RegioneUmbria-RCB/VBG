package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.GruppiIstruttoriDAO;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface GruppiIstruttoriService extends BaseService<GruppiIstruttori, PkId> {

    /**
     * @see GruppiIstruttoriDAO#findAll(Integer, Integer)
     */
    public List<GruppiIstruttori> findAll(Integer firstResult, Integer maxResult);

    public List<GruppiIstruttori> findByDescrizione(String textToSearch);
}
