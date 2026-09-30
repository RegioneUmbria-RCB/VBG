package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcValiditacoefficientiDAO;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author
 */
public interface CcValiditacoefficientiService extends BaseService<CcValiditacoefficienti, PkId> {

    /**
     * @see CcValiditacoefficientiDAO#findAll(Integer, Integer)
     */
    public List<CcValiditacoefficienti> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce una lista di {@link CcValiditacoefficienti} ordinata per intervallo di validità (ORDER BY DA, A)
     */
    public List<CcValiditacoefficienti> listByDataValidita();

    /**
     * Restituisce un oggetto {@link CcValiditacoefficienti} che rappresenta il coefficiente effettivamente in vigore
     * alla data passata come argomento al metodo. Se nessun coefficiente è in vigore a quella data il metodo
     * restituisce null.
     * 
     * @param validoAllaData
     * @return
     */
    public CcValiditacoefficienti findValidoAllaData(Date validoAllaData);

    /**
     * Ritorna una lista di record di CcValiditacoefficienti filtrati
     * 
     * @param filterTable
     * @return
     */
    public List<CcValiditacoefficienti> findByFilterTable(FilterTable filterTable);

    /**
     * Ritorna l'oggetto che ha come descrizione quella passata, se non esiste ritorna null
     * 
     * @param descrizione
     * @return
     */
    public CcValiditacoefficienti findByEqualsDescrizione(String descrizione);

    /**
     * Ritorna true se esistono record nella tabella per il software passato o quello corrente, altrimenti false
     * 
     * @return
     */
    public boolean existRecordByCurrentSoftware();
}
