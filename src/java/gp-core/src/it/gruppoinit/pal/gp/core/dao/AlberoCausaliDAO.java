/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoCausali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface AlberoCausaliDAO extends BaseDAO<AlberoCausali, PkId> {

    public List<AlberoCausali> findByAlberoProc(AlberoCausali entity);
}
