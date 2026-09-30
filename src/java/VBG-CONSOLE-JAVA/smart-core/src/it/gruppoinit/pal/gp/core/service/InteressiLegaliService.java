package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.InteressiLegali;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author francescop
 * 
 */
public interface InteressiLegaliService extends BaseService<InteressiLegali, Integer> {

    /**
     * Recupero dalla tabella InteressiLegali i record che hanno:<br/>
     * interessiLegali.dataInizio <= :DataFine and ( interessiLegali.dataFine >= :DataInizio or interessiLegali.dataFine
     * is null )
     * 
     * @param dataInizio
     * @param dataFine
     * @return
     */
    public List<InteressiLegali> findByDataInizioFine(Date dataInizio, Date dataFine);
}
