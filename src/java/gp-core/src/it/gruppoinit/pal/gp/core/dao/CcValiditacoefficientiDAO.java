package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author 
 */
public interface CcValiditacoefficientiDAO extends BaseDAO<CcValiditacoefficienti, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcValiditacoefficienti> findAll(Integer firstResult, Integer maxResult);
    
    /**
     * Restituisce una lista di {@link CcValiditacoefficienti} ordinata per intervallo di validità (ORDER BY DA, A) 
     */
    public List<CcValiditacoefficienti> listByDataValidita();
    
    /**
     * Restituisce un oggetto {@link CcValiditacoefficienti} che rappresenta il coefficiente effettivamente in vigore alla data 
     * passata come argomento al metodo. Se nessun coefficiente è in vigore a quella data il metodo restituisce null.
     * @param validoAllaData
     * @return
     */
    public CcValiditacoefficienti findValidoAllaData(Date validoAllaData);
}
