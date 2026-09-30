package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvv;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvvId;

import java.util.List;

/**
 * 
 * @author riccardob
 */
public interface ResponsabiliTmAvvService extends BaseService<ResponsabiliTmAvv, ResponsabiliTmAvvId> {

    /**
     * @throws NotImplementedException
     */
    public List<ResponsabiliTmAvv> findAll(Integer firstResult, Integer maxResult);

    public List<ResponsabiliTmAvv> findByResponsabile(Integer codiceresponsabile);

    /**
     * Restituisce la lista di ResponsabiliTmAvv filtrando per Responsabile e per flagEsclude
     * 
     * @param codiceresponsabile
     * @param flagEsclude
     * @return
     */
    public List<ResponsabiliTmAvv> findByResponsabile(Integer codiceresponsabile, Boolean flagEsclude);

    public List<ResponsabiliTmAvv> findByTipomovimento(String tipomovimento);
}
