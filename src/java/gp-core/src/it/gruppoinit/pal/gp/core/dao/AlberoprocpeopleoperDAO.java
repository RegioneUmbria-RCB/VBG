/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeopleoper;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface AlberoprocpeopleoperDAO extends BaseDAO<Alberoprocpeopleoper, PkId> {

    public List<Alberoprocpeopleoper> findByAlberoProc(Alberoproc alberoproc);
}
