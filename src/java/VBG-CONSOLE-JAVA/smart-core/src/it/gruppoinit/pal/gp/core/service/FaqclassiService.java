package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FaqclassiDAO;
import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface FaqclassiService extends BaseService<Faqclassi, PkId> {

    /**
     * @see FaqclassiDAO#findAll(Integer, Integer)
     */
    public List<Faqclassi> findAll(Integer firstResult, Integer maxResult);

    public List<Faqclassi> findByFaqclasse(String faqclasse);

    /**
     * Ritorna una lista di faq classi filtrando per software e per faq (ritornano solo le faq classi che sono associato
     * alemno ad una faq)
     * 
     * @param software
     *            : software per cui cercare
     * @param isCercaPerTT
     *            : se true cerca anche per software TT
     * @return
     */
    public List<Faqclassi> findBySoftwareAndFaq(String software, boolean isCercaPerTT);
}
