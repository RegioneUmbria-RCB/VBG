package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DehorsLogDAO;
import it.gruppoinit.pal.gp.core.domain.DehorsLog;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniCommand;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author
 */
public interface DehorsLogService extends BaseService<DehorsLog, PkId> {

    /**
     * @see DehorsLogDAO#findAll(Integer, Integer)
     */
    public List<DehorsLog> findAll(Integer firstResult, Integer maxResult);

    public DehorsLog populateDehorsLog(AutorizzazioniCommand autorizzazioniCommand, BigDecimal mqIniziali, BigDecimal mqvariati, String log);

    /**
     * Ritorna una lista di DehorsLog filtrando per autorizzazione
     * 
     * @param codice
     */
    public List<DehorsLog> findByAutorizzazione(Integer codiceAut);
}
