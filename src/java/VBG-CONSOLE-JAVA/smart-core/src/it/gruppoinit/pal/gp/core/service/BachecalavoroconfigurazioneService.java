package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BachecalavoroconfigurazioneDAO;
import it.gruppoinit.pal.gp.core.domain.Bachecalavoroconfigurazione;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface BachecalavoroconfigurazioneService extends BaseService<Bachecalavoroconfigurazione, String> {

    /**
     * @see BachecalavoroconfigurazioneDAO#findAll(Integer, Integer)
     */
    public List<Bachecalavoroconfigurazione> findAll(Integer firstResult, Integer maxResult);
}
