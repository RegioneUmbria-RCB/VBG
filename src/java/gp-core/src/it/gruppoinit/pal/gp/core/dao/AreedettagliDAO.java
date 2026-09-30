package it.gruppoinit.pal.gp.core.dao;

import java.math.BigDecimal;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Areedettagli;
import it.gruppoinit.pal.gp.core.domain.PkId;

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
    public List<Areedettagli> findByStradario(Integer codiceStradario);

    /**
     * ritorna la lista dei dettagli di area filtrati per l'area passata come argomento ordinati per stradario in dalla
     * A alla Z
     * 
     * @param area
     * @return
     */
    public List<Areedettagli> findByAree(Aree area);

    /**
     * Ritorna la lista dei dettagli delle aree filtrati per stradario e civico
     * 
     * @param codiceStradario
     * @param civico
     * @return
     */
    public List<Areedettagli> findByStradarioECivico(Integer codiceStradario, Integer civico);

    /**
     * Ritorna la lista dei dettagli delle aree filtrati per stradario e km
     * 
     * @param codiceStradario
     * @param km
     * @return
     */
    public List<Areedettagli> findByStradarioEKm(Integer codiceStradario, BigDecimal km);
}
