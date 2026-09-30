/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.PecInboxId;


/**
 * @author francol
 *
 */
public interface PecInboxDAO extends BaseDAO<PecInbox, PecInboxId> {
    
    /**
     * Ricerca tutti i record filtrati per idcomune e software ordinati per data ricezione decrescente
     */
    public List<PecInbox> findAll(Integer firstResult, Integer maxResult);

}
