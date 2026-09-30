package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author francescop
 */
public interface AlberoprocOneriService extends BaseService<AlberoprocOneri, PkId> {

    /**
     * Metodo che restituisce la lista di AlberoprocOneri filtrati per il codice Alberoproc
     * 
     * @param codiceAlberoproc
     * @return
     */
    public List<AlberoprocOneri> findAllByAlberoproc(String idcomune, Integer codiceAlberoproc);
}
