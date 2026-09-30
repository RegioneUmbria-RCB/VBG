/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.ContiProgressiviAnno;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface ContiProgressiviAnnoDAO extends BaseDAO<ContiProgressiviAnno, PkId> {

    public List<ContiProgressiviAnno> findContiProgressivi(Conti conti);
}
