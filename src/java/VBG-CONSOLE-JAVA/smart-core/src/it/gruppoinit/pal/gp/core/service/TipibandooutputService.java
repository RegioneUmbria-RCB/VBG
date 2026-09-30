/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface TipibandooutputService extends BaseService<Tipibandooutput, PkId> {

    List<Tipibandooutput> findByTipibando(Tipibandooutput tipibandooutput);

    /**
     * Lista di bandi output filtrati per graduatoria t
     * 
     * @param codiceGraduatoriat
     * @return
     */
    public List<Tipibandooutput> findByTipigraduatoriet(Integer codiceGraduatoriat);
}