package it.gruppoinit.pal.gp.core.service;

import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Movimentimailallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;

/**
 * 
 * @author francescop
 */
public interface MovimentimailallegatiService extends BaseService<Movimentimailallegati, PkId> {

    /**
     * Popola a partire dall'oggetto documentoHelper una Set di documenti mail allegati
     * 
     * @param documentiHelper
     * @return
     */
    public Set<Movimentimailallegati> findAllegatiMovimentiMailDaInviare(DocumentiHelper documentiHelper, Movimentimail movimentimail);
}
