package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TempificazioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface TempificazioniService extends BaseService<Tempificazioni, PkId> {

    /**
     * @see TempificazioniDAO#findAll(Integer firstResult, Integer maxResult)
     */
    public List<Tempificazioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see TempificazioniDAO#findByDescrizione(String tempificazione)
     */
    public List<Tempificazioni> findByDescrizione(String tempificazione);
}
