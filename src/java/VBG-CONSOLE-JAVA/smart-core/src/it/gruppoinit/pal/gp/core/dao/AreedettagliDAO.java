package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Areedettagli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;

/**
 * @author Riccardo Bocci
 * 
 */
public interface AreedettagliDAO extends BaseDAO<Areedettagli, PkId> {

    /**
     * ritorna la lista dei dettagli di area filtrati per lo stradario passato come argomento ordinati per stradario in
     * dalla A alla Z
     * 
     * @param stradario
     * @return
     */
    public List<Areedettagli> findByStradario(Stradario stradario);

    /**
     * ritorna la lista dei dettagli di area filtrati per l'area passata come argomento ordinati per stradario in dalla
     * A alla Z
     * 
     * @param area
     * @return
     */
    public List<Areedettagli> findByAree(Aree area);
}
