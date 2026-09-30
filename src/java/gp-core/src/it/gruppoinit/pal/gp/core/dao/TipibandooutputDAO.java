/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface TipibandooutputDAO extends BaseDAO<Tipibandooutput, PkId> {

    List<Tipibandooutput> findByGraduatoriat(Tipibandooutput tipibandooutput);
}
