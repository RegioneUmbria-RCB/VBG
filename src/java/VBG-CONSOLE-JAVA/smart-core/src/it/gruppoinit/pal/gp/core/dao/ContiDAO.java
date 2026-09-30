/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface ContiDAO extends BaseDAO<Conti, PkId> {

    public List<Conti> findByDescrizione(String descrizione);
}
