package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocProtocolloDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocProtAndFascHelper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author
 */
public interface AlberoprocProtocolloService extends BaseService<AlberoprocProtocollo, PkId> {

    /**
     * @see AlberoprocProtocolloDAO#findAll(Integer, Integer)
     */
    public List<AlberoprocProtocollo> findAll(Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Ritorna una lista di oggetti AlberoprocProtAndFascHelper che contengo il campo : comune : desc del comune
     * List<AlberoprocProtocollo> : lista di AlberoprocProtocollo
     * 
     * @param codiceAmministrazione
     * @param responsabile
     * @return
     * </pre>
     */
    public List<AlberoprocProtAndFascHelper> findByComuniPerOperatore(Integer codice, Responsabili responsabile);

    public List<AlberoprocProtocollo> findByAlberoprocId(Integer codiceAlberoproc, Integer firstResult, Integer maxResult);

    public Object findProprietaByAlberoprocId(Integer codiceAlberoproc, String propertyName, String codiceComune);

    public Map<String, AlberoprocProtocollo> findConfigurazioniHelper(Integer codiceAlberoproc);

    /**
     * trova la prima configurazione valida per quel comune in caso di assenza per comune nullo. Il metodo risale la
     * gerarchia dell'albero dei procedimenti e si ferma al primo trovato
     * 
     * @param codiceAlberoproc
     * @param codiceComune
     * @return
     */
    public AlberoprocProtocollo findByAlberoprocIdAndComune(Integer codiceAlberoproc, String codiceComune);

    /**
     * trova la prima configurazione dell'amministrazione valida per quel comune in caso di assenza per comune nullo. Il
     * metodo risale la gerarchia dell'albero dei procedimenti e si ferma al primo trovato
     * 
     * @param codiceAlberoproc
     * @param codiceComune
     * @return
     */
    public Integer findAmministrazioniByAlberoprocIdAndComune(Integer codiceAlberoproc, String codiceComune);
}
