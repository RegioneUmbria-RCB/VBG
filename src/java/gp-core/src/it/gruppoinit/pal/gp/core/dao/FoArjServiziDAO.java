package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface FoArjServiziDAO extends BaseDAO<FoArjServizi, PkId> {

    /**
     * recupera la lista dei servizi configurati per l'areariservata ordinata per urlServizio
     * 
     */
    public List<FoArjServizi> findAll(Integer firstResult, Integer maxResult);
    
    /**
     * recupera la lista dei servizi configurati per l'intervento selezionato
     * @param alberoproc
     * @return
     */
    public List<FoArjServizi> findByAlberoproc(Alberoproc alberoproc);
}
