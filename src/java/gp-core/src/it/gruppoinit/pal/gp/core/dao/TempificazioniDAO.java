package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface TempificazioniDAO extends BaseDAO<Tempificazioni, PkId> {

    /**
     * Lista delle tempificazioni filtrati per idcomune e ordinati per il campo tempificazione
     * 
     */
    public List<Tempificazioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * Lista di tempificazioni filtrate per descrizione (campo tempificazione)
     * 
     * @param tempificazione
     * @return
     */
    public List<Tempificazioni> findByDescrizione(String tempificazione);
}
