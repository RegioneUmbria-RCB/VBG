package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Quesiti;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author Luca Proietti
 */
public interface QuesitiDAO extends BaseDAO<Quesiti, PkId> {

    /**
     * 
     * @param softwareList
     * @return Una lista di Quesiti filtrata tramite la lista di Software abilitati per quel Responsabile
     */
    public List<Quesiti> findByFilter(Set<Software> softwareList);
}
