/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface MercatiDAO extends BaseDAO<Mercati, PkId> {

    /**
     * Restituisce i mercati (filtrando per idcomune e software) ordinandole per il campo descrizione
     */
    public List<Mercati> findAll(Integer firstResult, Integer maxResult);

    public List<Mercati> findByDescrizione(String descrizione);

    public List<Mercati> findByDescrizione(String descrizione, MercatiEnum mercatiEnum);

    /**
     * Cerca una lista di mercati che abbiano la contabilità abilitata o meno
     * 
     * @param descrizione
     *            la stringa per filtrare i mercati in base a descrizione
     * @param isFlagContabilita
     *            se la gestione della contabilità è attivata o meno
     * @param mercatiEnum
     *            tutti i mercati, solo quelli attivi, solo quelli disabilitati
     * @see it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum
     * @return una lista di mercati che corrisponde ai criteri di ricerca
     */
    public List<Mercati> findByFlagContabilita(String descrizione, boolean isFlagContabilita, MercatiEnum mercatiEnum);

    /**
     * Restituisce i mercati ATTIVI (filtrando per idcomune e software) ordinandole per il campo descrizione
     */
    public List<Mercati> findAllMercatiAttivi(Integer firstResult, Integer maxResult);
}
