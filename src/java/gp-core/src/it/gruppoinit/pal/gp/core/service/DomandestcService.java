package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DomandestcDAO;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DomandeSTCScadenzarioDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DomandestcHelper;
import it.gruppoinit.pal.gp.core.domain.web.DomandeStcFilter;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface DomandestcService extends BaseService<Domandestc, PkId> {

    /**
     * @see DomandestcDAO#findAll(Integer, Integer)
     */
    public List<Domandestc> findAll(Integer firstResult, Integer maxResult);

    public List<Domandestc> findByFilterTable(FilterTable filterTable);

    public List<Domandestc> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    public int countRecord(FilterTable filterTable);

    /**
     * Metodo che crea una filter table a partire dall'ogetto passato
     * 
     * @param domandestc
     * @return
     */
    public FilterTable createFilterTableByEntity(Domandestc domandestc);

    /**
     * Torna la lista delle domande provenienti da STC e legate alla pratica indicata
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Domandestc> findByIstanza(Integer codiceIstanza);

    /**
     * 
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Domandestc> findDomandeConErrore(Integer firstResult, Integer maxResult);

    /**
     * 
     * @param firstResult
     * @param maxResult
     * @return
     */
    public int countDomandeConErrore();

    /**
     * 
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Domandestc> findDomandePervenute(Integer firstResult, Integer maxResult);

    public List<DomandestcHelper> countDomandestc(String codicesoftware, String stato);

    /**
     * @see DomandestcDAO#findDomandePervenuteSTC(Integer firstResult, Integer maxResult)
     */
    public List<DomandeSTCScadenzarioDTO> findDomandePervenuteSTC(DomandeStcFilter domandeStcFilter, boolean isImportate, String codiceSoftwareSca,
	    Integer firstResult, Integer maxResult);

    /**
     * @see DomandestcDAO#countScadenzarioDomandePervenuteSTC(boolean isImportate)
     */
    public int countScadenzarioDomandePervenuteSTC(boolean isImportate, String codiceSoftwareSca, DomandeStcFilter domandeStcFilter);

    public List<Domandestc> findByCodiceIstanzaPrenotato(Integer codice);

    public void delete(Domandestc domandestc, boolean cancellaFoDomande);
}
