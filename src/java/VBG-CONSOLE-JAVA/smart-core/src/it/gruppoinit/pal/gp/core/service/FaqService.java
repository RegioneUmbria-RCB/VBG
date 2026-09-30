package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface FaqService extends BaseService<Faq, PkId> {

    /**
     * @see FaqDAO#findByFilter(Set<Software> softwareList)
     */
    public List<Faq> findByFilter(List<String> softwareList, Integer firstResult, Integer maxResult);

    /**
     * Ordinati per ordine, domanda
     */
    public List<Faq> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ordinati per ordine, domanda
     */
    public List<Faq> findAllPubblicate(Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * List Faq Ordinati per ordine, domanda
     * @param codiceFaqclassi
     * @param isPubblica : opzionale
     *                     true	: pubblicare==true
     *                     false: pubblicare==false
     *                     null	: non filtra per pubblcare
     * @param software
     * @param isCercaPerTT 
     * 
     * @return
     * </pre>
     */
    public List<Faq> findByFaqClassi(Integer codiceFaqclassi, Boolean isPubblica, String software, boolean isCercaPerTT);

    /**
     * Ordinati per ordine, domanda
     * 
     */
    /**
     * <pre>
     * List Faq Ordinati per ordine, domanda
     * @param codiceFaqclassi
     * @param isPubblica : opzionale
     *                     true	: pubblicare==true
     *                     false: pubblicare==false
     *                     null	: non filtra per pubblcare 
     * @param software
     * @param isCercaPerTT
     * @return
     * </pre>
     */
    public List<Faq> findWithoutFaqClassi(Boolean isPubblica, String software, boolean isCercaPerTT);
}
