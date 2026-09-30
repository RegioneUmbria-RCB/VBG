package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmSca;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmScaId;

import java.util.List;

/**
 * 
 * @author riccardob
 */
public interface ResponsabiliTmScaService extends BaseService<ResponsabiliTmSca, ResponsabiliTmScaId> {

    /**
     * @throws NotImplementedException
     */
    public List<ResponsabiliTmSca> findAll(Integer firstResult, Integer maxResult);

    public List<ResponsabiliTmSca> findByResponsabile(Integer codiceresponsabile);

    /**
     * Restituisce la lista di ResponsabiliTmSca filtrando per Responsabile e per flagEsclude
     * 
     * @param codiceresponsabile
     * @param flagEsclude
     * @return
     */
    public List<ResponsabiliTmSca> findByResponsabile(Integer codiceresponsabile, Boolean flagEsclude);

    public List<ResponsabiliTmSca> findByTipomovimento(String tipomovimento);
}
