package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestTestataDAO;

/**
 * 
 * @author
 */
public interface BollGestTestataService extends BaseService<BollGestTestata, PkId> {

    /**
     * @see BollGestTestataDAO#findAll(Integer, Integer)
     */
    public List<BollGestTestata> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce l'implementazione a partire dalla posizione debitoria
     * 
     * @param codice
     * @return
     */
    public String findImplementazioneByPosDeb(Integer codice);

    public Integer findCodIstanzaByDettPosDebitoria(Integer codice);
}
