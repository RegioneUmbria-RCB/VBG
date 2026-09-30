package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.Tipimovimentidyn2modellitDAO;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface Tipimovimentidyn2modellitService extends BaseService<Tipimovimentidyn2modellit, Tipimovimentidyn2modellitId> {

    /**
     * @see Tipimovimentidyn2modellitDAO#findAll(Integer, Integer)
     */
    public List<Tipimovimentidyn2modellit> findAll(Integer firstResult, Integer maxResult);

    public List<Tipimovimentidyn2modellit> findByTipimovimento(Tipimovimento tipimovimento);
}
