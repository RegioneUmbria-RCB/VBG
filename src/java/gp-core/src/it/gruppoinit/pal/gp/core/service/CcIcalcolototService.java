package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcIcalcolototDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotot;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface CcIcalcolototService extends BaseService<CcIcalcolotot, PkId> {

    /**
     * @see CcIcalcolototDAO#findAll(Integer, Integer)
     */
    public List<CcIcalcolotot> findAll(Integer firstResult, Integer maxResult);

    public List<CcIcalcolotot> findByFilterTable(FilterTable filterTable);

    /**
     * Ritorna il numero di oggetti CcIcalcolotot filtrati per CcValiditacoefficienti
     * 
     * @param entity
     * @return
     */
    public int countByCcValiditacoefficienti(CcValiditacoefficienti entity);

    /**
     * Ritorna una lista di CcIcalcolotot filtrate per istanza e ordinati per "descrizione"
     * 
     * @param istanze
     * @return
     */
    public List<CcIcalcolotot> findByIstanza(Istanze istanze);
}
