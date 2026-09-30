package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliAssenzeDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliAssenze;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author
 */
public interface ResponsabiliAssenzeService extends BaseService<ResponsabiliAssenze, PkId> {

    /**
     * @see ResponsabiliAssenzeDAO#findAll(Integer, Integer)
     */
    public List<ResponsabiliAssenze> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see ResponsabiliAssenzeDAO#findByResponsabile(Integer codice, Integer firstResult, Integer maxResult)
     */
    public List<ResponsabiliAssenze> findByResponsabile(Integer codice, Integer firstResult, Integer maxResult);

    public boolean isAssente(Responsabili responsabili, Date date);
}
