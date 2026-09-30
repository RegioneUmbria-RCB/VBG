/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Campigraduatoria;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface CampigraduatoriaService extends BaseService<Campigraduatoria, PkId> {

    /**
     * Metodo utilizzato per recuperare i campi graduatoria di una dettaglio graduatoria (GraduadoriaD).
     * 
     * @param codiceGraduadoriaD
     * @return
     */
    public List<Campigraduatoria> findByGraduatorieDAndOrderByOrdine(Integer codiceGraduadoriaD);
}
