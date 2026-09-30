/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.EndoCausali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface EndoCausaliService extends BaseService<EndoCausali, PkId> {

    public List<EndoCausali> findByInventarioprocedimenti(EndoCausali entity);
}
