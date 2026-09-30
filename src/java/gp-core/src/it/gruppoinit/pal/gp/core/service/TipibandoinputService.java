/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipibandoinput;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface TipibandoinputService extends BaseService<Tipibandoinput, PkId> {

    public List<Tipibandoinput> findTipiBandiInput(Tipibandoinput tipibandoinput);

    public List<Tipibandoinput> findByFilterTipobando(Tipibando tipibando);
}
