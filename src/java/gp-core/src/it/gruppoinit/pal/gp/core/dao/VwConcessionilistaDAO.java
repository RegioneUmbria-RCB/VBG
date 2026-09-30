package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.TipoQueryHelperEnum;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwConcessionilista;
import it.gruppoinit.pal.gp.core.domain.helper.ConcessioniListHelper;

import java.util.Date;
import java.util.List;

public interface VwConcessionilistaDAO extends BaseDAO<VwConcessionilista, PkId> {

    List<VwConcessionilista> findConcessioniCriteria(VwConcessionilista vwConcessionilista);

    /**
     * <p>
     * Ritorna una lista di VwConcessionilista filtrata. I record restituiti saranno psri al valore del parametro :
     * maxResults I record restituiti partiranno dal numero passato dal parametro: firstResult
     * 
     * @param vwConcessionilista
     * @param firstResult
     * @param maxResults
     * @return </p>
     */
    public List<VwConcessionilista> findConcessioniCriteria(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults);

    /**
     * <p>
     * Ritorna il numero dei record VwConcessionilista presenti nel db filtrati
     * 
     * @param vwConcessionilista
     * 
     * @return </p>
     */
    public int countByFilter(VwConcessionilista vwConcessionilista);

    /**
     * Ritorna una lista di ConcessioniListHelper filtrando VwConcessionilista
     * 
     * @param vwConcessionilista
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults);

    /**
     * Ritorna una lista di ConcessioniListHelper filtrando VwConcessionilista, se isSelectGroupByConcId la query
     * ritorna solo i campi conc_id,idcomune raggurppati
     * 
     * @param vwConcessionilista
     * @param firstResult
     * @param maxResults
     * @param isSelectGroupByConcId
     * @return
     */
    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults,
	    boolean isSelectGroupByConcId);

    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults,
	    TipoQueryHelperEnum tipoQueryHelperEnum);

    /**
     * Ritorna il numero di concessioni filtrando per VwConcessionilista
     * 
     * @param vwConcessionilista
     * @return
     */
    public int countConcessioniListHelper(VwConcessionilista vwConcessionilista);

    /**
     * Ritorna il numero di concessioni filtrando per VwConcessionilista, se isCountDistinctConcId = true allora dal
     * conteggio elimina quelle che hanno lo stesso valore di conc_id
     * 
     * @param vwConcessionilista
     * @return
     */
    public int countConcessioniListHelper(VwConcessionilista vwConcessionilista, boolean isCountDistinctConcId);

    public String exportModalitaPentaho(VwConcessionilista vwConcessionilista, Esportazioni esportazioni, Date data, String email,
	    String contestoExport, boolean isInvioMail);
}
