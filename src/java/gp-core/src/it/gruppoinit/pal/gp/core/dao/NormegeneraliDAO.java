package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Normegenerali;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author Luca Proietti
 */
public interface NormegeneraliDAO extends BaseDAO<Normegenerali, PkId> {

    /**
     * 
     * @param softwareList
     * @return Una lista di Norme Generali filtrata tramite la lista di Software abilitati per quel Responsabile e
     *         ordinata per il campo ordine ASC
     */
    public List<Normegenerali> findByFilter(Set<Software> softwareList);
}
