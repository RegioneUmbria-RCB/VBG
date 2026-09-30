package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentiincomp;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface InventarioprocedimentiincompDAO extends BaseDAO<Inventarioprocedimentiincomp, PkId> {

    /**
     * Ritorna una lista di endo procedimenti incompatibili
     * 
     */
    public List<Inventarioprocedimentiincomp> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di endo procedimenti incompatibili con un endo procedimento passato e ordianti per procedimento
     * 
     */
    public List<Inventarioprocedimentiincomp> findByEndoprocedimento(Inventarioprocedimenti inventarioprocedimenti);

    /**
     * Ritorna un oggetto endoprocedimentoincompatibile filtrando per un endoprocedimento e per un endoprocedimento
     * incompatibile se non lo trova ritorna null
     * 
     * @param inventarioprocedimenti
     * @param inventarioprocedimentoIncomp
     * @return
     */
    public Inventarioprocedimentiincomp findByEndoprocedimentoAndEndoprocedimentoIncomp(Inventarioprocedimenti inventarioprocedimenti,
	    Inventarioprocedimenti inventarioprocedimentoIncomp);
}
