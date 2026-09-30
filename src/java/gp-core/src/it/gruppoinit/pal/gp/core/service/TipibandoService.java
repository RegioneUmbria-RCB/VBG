/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface TipibandoService extends BaseService<Tipibando, PkId> {

    public List<Tipibando> findActiveTipibando();
}
