package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface FaqDAO extends BaseDAO<Faq, PkId> {

    /**
     * 
     * @param softwareList
     * @param firstResult
     * @param maxResult
     * @return Una lista di Faq filtrata tramite la lista di Software abilitati per quel Responsabile
     */
    public List<Faq> findByFilter(List<String> softwareList, Integer firstResult, Integer maxResult, Boolean pubblicare);
}
