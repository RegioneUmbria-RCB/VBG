package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipisoggettoDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.init.sigepro.rte.types.RuoloType;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface TipisoggettoService extends BaseService<Tipisoggetto, PkId> {

    /**
     * @see TipisoggettoDAO#findAll(Integer, Integer)
     */
    public List<Tipisoggetto> findAll(Integer firstResult, Integer maxResult);

    public List<Tipisoggetto> findByFilterTable(FilterTable filterTable);

    /**
     * <pre>
     * Se ruoloType è nullo allora torna nullo:
     * Nel caso che ruolotype non sia nullo:
     * 1. cerca TS per codice e Software
     * 3. cerca TS usando descrizione e software
     * 2. cerca TS per codice
     * 4. cerca TS usando descrizione
     * 5. usa il codice che si trova nella verticalizzazione STC--&gt;TIPO_SOGGETTO_DEFAULT
     * 6. se lookup fallisce allora rilancia eccezione
     * </pre>
     * 
     * @param ruoloType
     * @return
     */
    public Tipisoggetto findByRuoloType(RuoloType ruoloType, String codiceSoftware) throws BusinessValidationException;

    /**
     * Ritorna la lista di tipi soggetto per software e flagMostraDettIstanza uguale al valore passato
     * 
     * @return
     */
    public List<Tipisoggetto> findByflagMostraDettIstanza(Boolean value);
}
