/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AlberoCausali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface AlberoCausaliService extends BaseService<AlberoCausali, PkId> {

    public List<AlberoCausali> findByAlberoProc(AlberoCausali entity);
}
