package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Bachecalavoroconfigurazione;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface BachecalavoroconfigurazioneDAO extends BaseDAO<Bachecalavoroconfigurazione, String> {

    /**
     * Restituisce la Configurazione della Bacheca Lavoro per quel Comune
     * 
     */
    public List<Bachecalavoroconfigurazione> findAll(Integer firstResult, Integer maxResult);
}
