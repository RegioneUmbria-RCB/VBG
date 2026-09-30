/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PecInboxAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;


/**
 * @author francol
 *
 */
public interface PecInboxAllegatiDAO extends BaseDAO<PecInboxAllegati, PkId> {
    
    public List<PecInboxAllegati> findAllegatiPec(String codicePec);
}
