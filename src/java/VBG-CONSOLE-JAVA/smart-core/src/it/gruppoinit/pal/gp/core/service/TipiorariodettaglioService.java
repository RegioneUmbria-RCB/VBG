package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;
import it.gruppoinit.pal.gp.core.domain.Tipiorariodettaglio;

/**
 * 
 * @author lucap
 */
public interface TipiorariodettaglioService extends BaseService<Tipiorariodettaglio, PkId> {

    /**
     * Cancella tutti i record di Tipiorariodettaglio di un Tipiorario
     * 
     * @param tipiorario
     */
    public void deleteByTipiorario(Tipiorario tipiorario);
}
