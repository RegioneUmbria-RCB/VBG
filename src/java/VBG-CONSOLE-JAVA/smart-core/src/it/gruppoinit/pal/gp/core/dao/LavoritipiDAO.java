package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface LavoritipiDAO extends BaseDAO<Lavoritipi, PkId> {

    /**
     * Torna la lista di lavori tipi di un determinato modulo software ordinati per lavoro dalla A alla Z
     * 
     */
    public List<Lavoritipi> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista di lavori tipi di un determinato modulo software e una determinata categoria ordinati per lavoro
     * dalla A alla Z
     * 
     * @param categoria
     * @return
     * @throws IllegalArgumentException
     *             se categoria è nullo o con codice vuoto
     */
    public List<Lavoritipi> findByLavoricategorie(Lavoricategorie categoria);

    /**
     * torna una lista di lavori tipi del modulo software corrente con il criterio di ricerca lavoro = ilike
     * %descrizione% ordinati per lavoro dalla A alla Z
     * 
     * @param descrizione
     * @return
     */
    public List<Lavoritipi> findByDescrizione(String descrizione);
}
