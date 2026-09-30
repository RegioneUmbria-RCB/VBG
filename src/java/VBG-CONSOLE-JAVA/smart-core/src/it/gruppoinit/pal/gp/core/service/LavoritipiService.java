package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.LavoritipiDAO;
import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface LavoritipiService extends BaseService<Lavoritipi, PkId> {

    /**
     * @see LavoritipiDAO#findAll(Integer, Integer)
     */
    public List<Lavoritipi> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see LavoritipiDAO#findByLavoricategorie(Lavoricategorie)
     */
    public List<Lavoritipi> findByLavoricategorie(Lavoricategorie categoria);

    /**
     * @see LavoritipiDAO#findByDescrizione(String)
     */
    public List<Lavoritipi> findByDescrizione(String descrizione);

    public List<Lavoritipi> findByFilterTable(FilterTable filterTable);
}
