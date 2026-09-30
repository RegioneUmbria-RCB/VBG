package it.gruppoinit.pal.gp.core.service;

import java.math.BigDecimal;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.AreedettagliDAO;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Areedettagli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;

/**
 * @author Riccardo Bocci
 * 
 */
public interface AreedettagliService extends BaseService<Areedettagli, PkId> {

    /**
     * @see AreedettagliDAO#findByStradario(codiceStradario)
     */
    public List<Areedettagli> findByStradario(Integer codiceStradario);

    public List<Areedettagli> findByStradarioECivico(Integer codiceStradario, Integer civico);

    public List<Areedettagli> findByStradarioEKm(Integer codiceStradario, BigDecimal km);

    /**
     * @see AreedettagliDAO#findByAree(Aree)
     */
    public List<Areedettagli> findByAree(Aree area);

    /**
     * Ritorna il numero dei record presenti nella tabella filtrati per stradario
     * 
     * @param filterTable
     * @return
     */
    public int countRecordByStradario(Stradario stradario);
}
