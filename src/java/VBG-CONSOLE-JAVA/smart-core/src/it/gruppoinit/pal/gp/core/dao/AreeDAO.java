package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * 
 * @author Riccardo Bocci
 * 
 */
public interface AreeDAO extends BaseDAO<Aree, PkId> {

    /**
     * Ricerca tutti i record filtrati per idcomune e software ordinati per denominazione
     */
    public List<Aree> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ricerca tutti i record filtrati per idcomune, software e like ({@link Restrictions#ilike},
     * {@link MatchMode#ANYWHERE}) su campo denominazione ordinati per denominazione
     * 
     * @param descrizione
     *            la stringa, o sottostringa per la quale effettuare la ricerca
     * @param codiceComune
     *            il comune per il quale si ricercano i dati
     * @param codiciComuniAbilitati
     *            la lista dei comuni abilitati sul quale limitare la ricerca
     * @return
     */
    public List<Aree> findByDescrizione(String descrizione, String codiceComune, String[] codiciComuniAbilitati);

    /**
     * Torna una lista di Aree ordinate per comune, denominazione per i comuni indicati come parametri
     * 
     * @param codiciComune
     * @return
     */
    public List<Aree> findAllByCodiciComuni(String[] codiciComune);
}
