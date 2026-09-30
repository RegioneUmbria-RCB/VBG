package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipisoggettopeopleDAO;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipisoggettopeople;
import it.gruppoinit.pal.gp.core.domain.TipisoggettopeopleId;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.init.sigepro.rte.types.RuoloType;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface TipisoggettopeopleService extends BaseService<Tipisoggettopeople, TipisoggettopeopleId> {

    /**
     * @see TipisoggettopeopleDAO#findAll(Integer, Integer)
     */
    public List<Tipisoggettopeople> findAll(Integer firstResult, Integer maxResult);

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

    public boolean existsByTipoSoggettoAndMappatura(Integer codice, String mappaturaDI);
}
