package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AreedettagliDAO;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Areedettagli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;

import java.util.List;

/**
 * @author Riccardo Bocci
 * 
 */
public interface AreedettagliService extends BaseService<Areedettagli, PkId> {

    /**
     * @see AreedettagliDAO#findByStradario(Stradario)
     */
    public List<Areedettagli> findByStradario(Stradario stradario);

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
