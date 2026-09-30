/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface VwAlberoprocDAO extends BaseDAO<VwAlberoproc, PkId> {

    List<VwAlberoproc> findByFilter(VwAlberoproc entity);
}
