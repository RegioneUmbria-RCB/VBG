/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.EndoCausali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface EndoCausaliDAO extends BaseDAO<EndoCausali, PkId> {

    public List<EndoCausali> findByInventarioprocedimenti(EndoCausali entity);
}
