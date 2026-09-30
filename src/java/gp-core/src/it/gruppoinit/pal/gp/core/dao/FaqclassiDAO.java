package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface FaqclassiDAO extends BaseDAO<Faqclassi, PkId> {

    /**
     * Torna una lista di Categorie FAQ ordinate per descrizione ASC
     * 
     */
    public List<Faqclassi> findAll(Integer firstResult, Integer maxResult);

    public List<Faqclassi> findByFaqclasse(String faqclasse);

    public List<Faqclassi> findBySoftwareAndFaq(String software, boolean isCercaPerTT);
}
