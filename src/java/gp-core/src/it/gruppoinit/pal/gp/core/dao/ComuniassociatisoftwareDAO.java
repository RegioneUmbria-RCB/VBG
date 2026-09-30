package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface ComuniassociatisoftwareDAO extends BaseDAO<Comuniassociatisoftware, PkId> {

    /**
     * Lista di comuni associati software
     */
    public List<Comuniassociatisoftware> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera il comune associato sotware per il comune passato, se viene passato null allora cerca se esiste il
     * record comune associato software con codice comune uguale a null
     * 
     * @param comuni
     * @return
     */
    public Comuniassociatisoftware findByComune(Comuni comuni);
}
