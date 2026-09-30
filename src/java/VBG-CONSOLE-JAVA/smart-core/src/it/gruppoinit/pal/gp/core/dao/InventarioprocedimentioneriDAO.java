package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface InventarioprocedimentioneriDAO extends BaseDAO<Inventarioprocedimentioneri, PkId> {

    /**
     * List di oneri degli inventario procedimenti (endo procediemnti) filtrati per software e id comune
     * 
     */
    public List<Inventarioprocedimentioneri> findAll(Integer firstResult, Integer maxResult);
    
    /**
     * Restituisce la lista degli oneri attivi su una lista di endoprocedimenti per idcomune e codicecomune 
     * @param idComune
     * @param codiceComuneGruppo
     * @param pkProcedimenti
     * @param escludiDisattivi 
     * @return
     */
    public List<Inventarioprocedimentioneri> findOneriPerProcedimenti(String idComune, String codiceComuneGruppo, List<PkId> pkProcedimenti, boolean escludiDisattivi);
}
