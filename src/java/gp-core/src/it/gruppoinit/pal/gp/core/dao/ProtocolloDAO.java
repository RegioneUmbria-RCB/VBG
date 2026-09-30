package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Protocollo;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface ProtocolloDAO extends BaseDAO<Protocollo, PkId> {

    /**
     * Restituisce la lista di Protocollo d'Intesa filtrata per Idcomune e ordinata per ordine ASC
     * 
     */
    public List<Protocollo> findAll(Integer firstResult, Integer maxResult);
}
