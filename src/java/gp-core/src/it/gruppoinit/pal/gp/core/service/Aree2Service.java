package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.Aree2DAO;
import it.gruppoinit.pal.gp.core.domain.Aree2;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface Aree2Service extends BaseService<Aree2, PkId> {

    /**
     * @see Aree2DAO#findAll(Integer, Integer)
     */
    public List<Aree2> findAll(Integer firstResult, Integer maxResult);

    public boolean existsRecords();

    @DeletableCacheElements
    public void resetObjectCached();

    /**
     * Torna tutti i record della tabella filtrati per criterio di ilike %denominazione% sulla descrizione passata
     * ordinati per denominazione ASC
     * 
     * @param descrizione
     * @return
     */
    public List<Aree2> findByDescrizione(String descrizione);
}
