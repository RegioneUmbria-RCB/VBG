/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeoplehref;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface AlberoprocpeoplehrefDAO extends BaseDAO<Alberoprocpeoplehref, PkId> {

    public List<Alberoprocpeoplehref> findByAlberoProc(Alberoproc alberoproc);
}
