/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface ContiService extends BaseService<Conti, PkId> {

    public List<Conti> findByDescrizione(String descrizione);
}
