package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.helper.TipoQueryHelperEnum;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwConcessionilista;
import it.gruppoinit.pal.gp.core.domain.helper.ConcessioniListHelper;

import java.util.Date;
import java.util.List;

public interface VwConcessionilistaService extends BaseService<VwConcessionilista, PkId> {

    public List<VwConcessionilista> findConcessioniCriteria(VwConcessionilista vwConcessionilista);

    /**
     * vedi doc del DAO
     * 
     * @see VwConcessionilistaiDAO#findConcessioniCriteria(VwConcessionilista vwConcessionilista, Integer firstResult,
     *      Integer maxResults)
     */
    public List<VwConcessionilista> findConcessioniCriteria(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults);

    /**
     * vedi doc del DAO
     * 
     * @see VwConcessionilistaiDAO#findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult,
     *      Integer maxResults, boolean isSelectGroupByConcId)
     */
    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults,
	    boolean isSelectGroupByConcId);

    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults,
	    TipoQueryHelperEnum tipoQueryHelperEnum);

    /**
     * vedi doc del DAO
     * 
     * @see VwConcessionilistaiDAO#countByFilter(VwConcessionilista vwConcessionilista)
     */
    public int countByFilter(VwConcessionilista vwConcessionilista);

    public byte[] exportConcessioni(VwConcessionilista vwConcessionilista, Integer codiceEsp, String idComuneEsportazione, String email,
	    boolean isInviaMail);

    public void clear();

    public VwConcessionilista populateFilterCessateUltimoMese();

    public VwConcessionilista populateFilterSubentriUltimoMese();

    public VwConcessionilista populateFilterRilasciUltimoMese();

    /**
     * 
     * @param codiceIstanza
     * @param codiceMercato
     * @return Ritorna true se esiste almeno una concessione attiva per questi filtri, altrimenti false
     */
    public boolean isConcessionePresenteByMercato(Integer codiceIstanza, Integer codiceMercato);

    /**
     * <pre>
     * Verifica se esiste una concessione attiva per il mercato, mercato uso e posteggio
     * 
     * @param codiceMercato
     * @param codiceMercatoUso
     * @param codicePosteggio
     * @return Ritorna true se esiste almeno una concessione per questi filtri, altrimenti false
     * </pre>
     */
    public boolean isConcessionePresenteByMercatoAndUsoAndPosteggio(Integer codiceMercato, Integer codiceMercatoUso, Integer codicePosteggio);

    /**
     * 
     * 
     * @see VwConcessionilistaiDAO#findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult,
     *      Integer maxResults)
     */
    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults);

    /**
     * 
     * 
     * @see VwConcessionilistaiDAO#countConcessioniListHelper(VwConcessionilista vwConcessionilista)
     */
    public int countConcessioniListHelper(VwConcessionilista vwConcessionilista);

    /**
     * 
     * 
     * @see VwConcessionilistaiDAO#countConcessioniListHelper(VwConcessionilista vwConcessionilista, boolean
     *      isCountDistinctConcId)
     */
    public int countConcessioniListHelper(VwConcessionilista vwConcessionilista, boolean isCountDistinctConcId);

    public String exportModalitaPentaho(VwConcessionilista vwConcessionilista, Esportazioni esportazioni, Date data, String email,
	    String contestoExport, boolean isInvioMail);
}
