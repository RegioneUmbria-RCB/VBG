package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CommedilizieAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface CommedilizieAllegatiDAO extends BaseDAO<CommedilizieAllegati, PkId> {

    /**
     * Lista di commisssioni edilizie allegati filtrat per idcomune
     * 
     */
    public List<CommedilizieAllegati> findAll(Integer firstResult, Integer maxResult);
}
